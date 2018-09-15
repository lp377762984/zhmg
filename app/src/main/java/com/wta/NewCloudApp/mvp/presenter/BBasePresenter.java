package com.wta.NewCloudApp.mvp.presenter;


import android.app.Activity;
import android.app.Dialog;

import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.config.DefaultHandleSubscriber;
import com.wta.NewCloudApp.config.HttpResponseHandler;
import com.wta.NewCloudApp.mvp.model.entity.Resend;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.ui.activity.LoginActivity;
import com.wta.NewCloudApp.mvp.ui.listener.DetDialogCallback;
import com.wta.NewCloudApp.mvp.view.BaseDataView;
import com.wta.NewCloudApp.uitls.DialogUtils;

import org.simple.eventbus.Subscriber;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import timber.log.Timber;


public class BBasePresenter<M extends IModel, V extends IView> extends BasePresenter<M, V> implements HttpResponseHandler {
    @Inject
    RxErrorHandler mErrorHandler;
    protected Resend resend;

    public BBasePresenter(M model, V rootView) {
        super(model, rootView);
    }

    @Override
    public void handle10(int what, Result result) {
        ArmsUtils.snackbarText(result.msg);
    }

    @Override
    public void handle11(int what, Result result) {
        ArmsUtils.snackbarText(result.msg);
    }

    @Override
    public void handle20(int what, Result result) {
        Activity topActivity = App.getInstance().getAppComponent().appManager().getTopActivity();
        DialogUtils.showAlertDialog(topActivity, result.msg, new DetDialogCallback() {
            @Override
            public void handleRight(Dialog dialog) {
                LoginActivity.startLogin(topActivity, BBasePresenter.this.getClass().getSimpleName());
            }
        });
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        if (result.login_access != null) {
            String sessionId = result.login_access.sessionId;
            AppConfig.getInstance().putString("sessionId", sessionId);
        }
    }

    @Override
    public <T> void handle404(int what, Result<T> result) {
        showToast(result.msg);
        if (result.login_access != null) {
            String sessionId = result.login_access.sessionId;
            AppConfig.getInstance().putString("sessionId", sessionId);
        }
    }

    @Override
    public <K> void handleOther(int what, K k) {

    }

    @Override
    public void handleException(int what, Throwable t) {

    }

    @Override
    public void handleExcept200(int what, Result rt) {

    }

    //执行网络请求
    protected <T> void doRequest(Observable<T> observable, RxErrorHandler errorHandler, int what, HttpResponseHandler handler) {
        resend = new Resend((observable), what);
        observable.subscribe(new DefaultHandleSubscriber<T>(errorHandler, what, handler));
    }

    //执行网络请求
    protected <T> void doRequest(Observable<T> observable, RxErrorHandler errorHandler, int what) {
        doRequest(observable, errorHandler, what, this);
    }

    protected <T> void doRequest(Observable<T> observable, int what) {
        doRequest(observable, mErrorHandler, what, this);
    }


    //生成网络请求
    protected <T> Observable<T> buildRequest(boolean isList, Observable<T> observable, boolean needLoading) {
        return observable
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (!isList && needLoading) mRootView.showLoading();
                    else if (isList) {
                        if (mRootView instanceof BaseDataView )
                            ((BaseDataView) mRootView).showListLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())//
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (isList) {
                        if (mRootView instanceof BaseDataView)
                            ((BaseDataView) mRootView).hideListLoading();
                    } else {
                        if (needLoading) mRootView.hideLoading();
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView));
    }

    //生成网络请求
    protected <T> Observable<T> buildRequest(Observable<T> observable) {
        return buildRequest(false, observable, true);
    }

    //生成列表网络请求
    protected <T> Observable<T> buildListRequest(Observable<T> observable) {
        return buildRequest(true, observable, false);
    }

    /**
     * 登陆之后自动重新请求
     */
    @Subscriber
    public void reDoRequest(String className) {
        if (resend != null && className.equals(this.getClass().getSimpleName())) {
            Observable observable = resend.observable;
            doRequest(observable, mErrorHandler, resend.what);
        }
    }

    public void showToast(String content) {
        ArmsUtils.makeText(App.getInstance(), content);
    }

}
