package com.wta.NewCloudApp.mvp.presenter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.alipay.sdk.app.AuthTask;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.ArmsUtils;
import com.wta.NewCloudApp.BuildConfig;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.config.DefaultHandleSubscriber;
import com.wta.NewCloudApp.mvp.contract.HomeContract;
import com.wta.NewCloudApp.mvp.model.entity.AliInfo;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.HomeBanner;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.Update;
import com.wta.NewCloudApp.mvp.ui.activity.MainActivity;
import com.wta.NewCloudApp.uitls.ConfigTag;
import com.wta.NewCloudApp.uitls.FileUtils;
import com.wta.NewCloudApp.uitls.FinalUtils;
import com.wta.NewCloudApp.uitls.PackageUtils;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.progressmanager.ProgressListener;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.progressmanager.body.ProgressInfo;
import okhttp3.ResponseBody;
import timber.log.Timber;


@FragmentScope
public class HomePresenter extends BBasePresenter<HomeContract.Model, HomeContract.View> {
    private ProgressInfo mLastDownloadingInfo;
    private int position;

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }

    public void getMsgList() {
        doRequest(buildRequest(false, mModel.getBillList(), true), 1);
    }

    public void getStoreState() {
        doRequest(buildRequest(mModel.getStoreState()), 2);
    }

    public void getHomeBanner() {
        doRequest(buildRequest(mModel.getHomeBanner()), 3);
    }

    public void checkUpdate() {
        doRequest(buildRequest(mModel.checkUpdate(PackageUtils.getPackageVersion(App.getInstance()))), 4);
    }

    //-------------------------------start
    public void getAlipayAuthInfo() {
        doRequest(buildRequest(mModel.getAlipayAuthInfo()), 5);
    }

    public void getAlipayClient(String payInfo) {
        Observable<Map<String, String>> mapObservable = Observable.create(new ObservableOnSubscribe<Map<String, String>>() {
            @Override
            public void subscribe(ObservableEmitter<Map<String, String>> emitter) throws Exception {
                emitter.onNext(requestAlipayClient(payInfo));
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        doRequest(mapObservable, 6);
    }

    private Map<String, String> requestAlipayClient(String payInfo) {
        AuthTask authTask = new AuthTask(mRootView.getMActivity());
        return authTask.authV2(payInfo, true);
    }

    private void uploadOpenId(String openID) {
        doRequest(buildRequest(mModel.bindAlipay(openID, "alipay")), 7);
    }
    //---------------------------------end

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            stopRefresh(what);
            mRootView.showList((Result<List<Bill>>) result);
        } else if (what == 2) {
            mRootView.showBState((Result<Business>) result);
        } else if (what == 3) {
            stopRefresh(what);
            mRootView.showHomeBanner(((List<HomeBanner>) result.data));
        } else if (what == 4) {
            mRootView.showUpdate(((Update) result.data));
        } else if (what == 5) {
            getAlipayClient(((AliInfo) result.data).info);
        } else if (what == 7) {
            mRootView.bindAliSuccess();
        }
    }

    @Override
    public <T> void handle404(int what, Result<T> result) {
        if (what == 2 || what == 5 || what == 7)
            super.handle404(what, result);
        else
            mRootView.showListFailed();
        stopRefresh(what);
    }

    @Override
    public void handle20(int what, Result result) {
        if (what != 1)
            super.handle20(what, result);
        else
            mRootView.showListFailed();
        stopRefresh(what);
    }

    @Override
    public void handle10(int what, Result result) {
        if (what != 1)
            super.handle10(what, result);
        else
            mRootView.showListFailed();
        stopRefresh(what);
    }

    @Override
    public void handle11(int what, Result result) {
        if (what != 1)
            super.handle11(what, result);
        else
            mRootView.showListFailed();
        stopRefresh(what);
    }

    @Override
    public void handleException(int what, Throwable t) {
        super.handleException(what, t);
        if (what == 1) mRootView.showListFailed();
        stopRefresh(what);
    }

    private void stopRefresh(int what) {
        if (what == 1 || what == 3) {
            mRootView.stopRefresh();
        }
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
                        File parentFile = new File(Environment.getExternalStorageDirectory() + "/temp");
                        if (!parentFile.exists()) parentFile.mkdirs();
                        File targetFile = new File(parentFile, "zhmg.apk");
                        FileUtils.writeToFile(inputStream, targetFile);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultHandleSubscriber<>(mErrorHandler));
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

    public void switchServer() {
        if (BuildConfig.DEBUG) {
            showServerSwitch();
        }
    }

    private void showServerSwitch() {
        final String items[] = {FinalUtils.SEVER_DEBUG, FinalUtils.SEVER_RELEASE};
        AlertDialog.Builder builder = new AlertDialog.Builder(mRootView.getMActivity());
        builder.setTitle("选择服务端地址");
        builder.setIcon(R.mipmap.app_logo);
        builder.setSingleChoiceItems(items, FinalUtils.SERVER_URL.equals(items[0]) ? 0 : 1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        position = which;
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppConfig.getInstance().putString(ConfigTag.SERVER_URL, items[position]);
                dialog.dismiss();
                //手动退出app
                //ArmsUtils.obtainAppComponentFromContext(mRootView.getMActivity()).appManager().appExit();
            }
        });
        builder.create().show();
    }

    @Override
    public <K> void handleOther(int what, K k) {
        super.handleOther(what, k);
        Map<String, String> k1 = (Map<String, String>) k;
        String resultStatus = k1.get("resultStatus");
        if ("9000".equals(resultStatus)) {
            String result = k1.get("result");
            handleResults(result);
        } else if ("6001".equals(resultStatus)) {
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

    @Override
    protected boolean isActivity() {
        return false;
    }

}
