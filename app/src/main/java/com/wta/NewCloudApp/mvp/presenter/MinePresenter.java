package com.wta.NewCloudApp.mvp.presenter;

import com.alipay.sdk.app.AuthTask;
import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.MineContract;
import com.wta.NewCloudApp.mvp.model.UserModel;
import com.wta.NewCloudApp.mvp.model.entity.AliInfo;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.Share;
import com.wta.NewCloudApp.mvp.model.entity.User;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@ActivityScope
public class MinePresenter extends BBasePresenter<UserModel, MineContract.View> {

    @Inject
    public MinePresenter(UserModel model, MineContract.View rootView) {
        super(model, rootView);
    }

    public void startShare() {
        doRequest(buildRequest(mModel.getShare()), 0);
    }

    public void getUserInfo() {
        doRequest(buildRequest(true, mModel.getUserInfo(), false), 1);
    }

    public void getAlipayAuthInfo() {
        doRequest(buildRequest(mModel.getAlipayAuthInfo()), 2);
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
        doRequest(buildRequest(mModel.bindAlipay(openID,"alipay")), 4);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 0) {
            mRootView.share((Result<Share>) result);
        } else if (what == 1) {
            mRootView.showUser((Result<User>) result);
        } else if (what == 2) {
            getAlipayClient(((AliInfo) result.data).info);
        } else  if (what==4){
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

    @Override
    protected boolean isActivity() {
        return false;
    }
}
