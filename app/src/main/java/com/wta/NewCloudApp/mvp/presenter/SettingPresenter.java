package com.wta.NewCloudApp.mvp.presenter;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.RxLifecycleUtils;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.config.DefaultHandleSubscriber;
import com.wta.NewCloudApp.mvp.contract.SettingContract;
import com.wta.NewCloudApp.mvp.model.UserModel;
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

    public void bindWX(Map<String, String> map) {
        doRequest(buildRequest(mModel.bindWX(map)), 1);
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

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            showToast(result.msg);
            mRootView.showData((User) result.data);
        } else if (what == 2) {
            mRootView.showUpdate((Update) result.data);
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
