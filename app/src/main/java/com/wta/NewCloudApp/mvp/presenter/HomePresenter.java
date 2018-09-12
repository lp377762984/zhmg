package com.wta.NewCloudApp.mvp.presenter;

import android.os.Environment;
import android.support.annotation.NonNull;

import com.jess.arms.di.scope.FragmentScope;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.config.DefaultHandleSubscriber;
import com.wta.NewCloudApp.mvp.contract.HomeContract;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.HomeBanner;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.Update;
import com.wta.NewCloudApp.uitls.FileUtils;
import com.wta.NewCloudApp.uitls.PackageUtils;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

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
        }
    }

    @Override
    public <T> void handle404(int what, Result<T> result) {
        if (what != 1 && what != 4)
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
                        File file = new File(Environment.getExternalStorageDirectory() + "/temp/zhmg.apk");
                        FileUtils.writeToFile(inputStream, file);
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
}
