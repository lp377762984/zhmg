package com.wta.NewCloudApp.mvp.presenter;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.alipay.sdk.app.AuthTask;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.RxLifecycleUtils;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.config.DefaultHandleSubscriber;
import com.wta.NewCloudApp.mvp.contract.SettingContract;
import com.wta.NewCloudApp.mvp.model.UserModel;
import com.wta.NewCloudApp.mvp.model.WXUserInfo;
import com.wta.NewCloudApp.mvp.model.entity.AliInfo;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.Update;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.uitls.ConfigTag;
import com.wta.NewCloudApp.uitls.FileUtils;
import com.wta.NewCloudApp.uitls.FinalUtils;
import com.wta.NewCloudApp.uitls.PackageUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.progressmanager.ProgressListener;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.progressmanager.body.ProgressInfo;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import okhttp3.ResponseBody;
import rx.functions.Action1;
import rx.functions.Func1;
import timber.log.Timber;


@ActivityScope
public class SettingPresenter extends BBasePresenter<UserModel, SettingContract.View> {
    private ProgressInfo mLastDownloadingInfo;
    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public SettingPresenter(UserModel model, SettingContract.View rootView) {
        super(model, rootView);
    }

    public void bindWX(WXUserInfo into) {
        doRequest(buildRequest(mModel.bindWX(into)), 1);
    }

    public void checkUpdate() {
        doRequest(buildRequest(mModel.checkUpdate(PackageUtils.getPackageVersion(App.getInstance()))), 2);
    }

    public void downLoadApp(String url) {
        ProgressManager.getInstance().addResponseListener(url, getDownloadListener());
        mModel.downloadApp(url)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showProgress();
                })
                .unsubscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, InputStream>() {
                    @Override
                    public InputStream apply(ResponseBody responseBody) throws Exception {
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.computation()) // 用于计算任务
                .doOnNext(new Consumer<InputStream>() {
                    @Override
                    public void accept(InputStream inputStream) throws Exception {
                        File file = new File(Environment.getExternalStorageDirectory() + "/temp/zhmg.apk");
                        FileUtils.writeToFile(inputStream, file);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultHandleSubscriber<>(mErrorHandler));
    }

    public void bindAli() {
        doRequest(buildRequest(mModel.getAlipayAuthInfo()), 3);
    }

    public void getAlipayClient(String payInfo) {
        Observable<Map<String, String>> mapObservable = Observable.create(new ObservableOnSubscribe<Map<String, String>>() {
            @Override
            public void subscribe(ObservableEmitter<Map<String, String>> emitter) throws Exception {
                emitter.onNext(requestAlipayClient(payInfo));
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        doRequest(mapObservable, 4);
    }

    private Map<String, String> requestAlipayClient(String payInfo) {
        AuthTask authTask = new AuthTask(mRootView.getActivityCet());
        return authTask.authV2(payInfo, true);
    }

    private void uploadOpenId(String openID) {
        doRequest(buildRequest(mModel.bindAlipay(openID,"alipay")), 5);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            showToast(result.msg);
            mRootView.showData((User) result.data);
        } else if (what == 2) {
            mRootView.showUpdate((Update) result.data);
        } else if (what == 3) {
            getAlipayClient(((AliInfo) result.data).info);
        } else if (what==5){
            mRootView.bindAliSuccess();
        }
    }

    @Override
    public <K> void handleOther(int what, K k) {
        super.handleOther(what, k);
        Map<String, String> k1 = (Map<String, String>) k;
        String resultStatus = k1.get("resultStatus");
        if ("9000".equals(resultStatus)) {
            String result = k1.get("result");
            handleResults(result);
        } else if ("6001".equals(resultStatus)){
            showToast("支付宝绑定已取消");
        } else {
            showToast("支付宝授权失败，code:" + resultStatus);
        }
    }

    private void handleResults(String result) {
        String[] split = result.split("&");
        for (String ss : split) {
            if (ss.contains("alipay_open_id")) {
                String[] split1 = ss.split("=");
                uploadOpenId(split1[1]);
            }
        }
    }

    @NonNull
    private ProgressListener getDownloadListener() {
        return new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                if (mLastDownloadingInfo == null) {
                    mLastDownloadingInfo = progressInfo;
                }
                if (progressInfo.getId() < mLastDownloadingInfo.getId()) {
                    return;
                } else if (progressInfo.getId() > mLastDownloadingInfo.getId()) {
                    mLastDownloadingInfo = progressInfo;
                }
                int progress = mLastDownloadingInfo.getPercent();
                mRootView.updateProgress(progress);
            }

            @Override
            public void onError(long id, Exception e) {

            }
        };
    }
}
