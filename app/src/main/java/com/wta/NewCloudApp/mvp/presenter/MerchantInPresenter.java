package com.wta.NewCloudApp.mvp.presenter;

import com.alipay.sdk.app.AuthTask;
import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.MerchantInContract;
import com.wta.NewCloudApp.mvp.model.BusinessModel;
import com.wta.NewCloudApp.mvp.model.entity.AliInfo;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.User;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@ActivityScope
public class MerchantInPresenter extends BBasePresenter<BusinessModel, MerchantInContract.View> {

    @Inject
    public MerchantInPresenter(BusinessModel model, MerchantInContract.View rootView) {
        super(model, rootView);
    }

    public void getAuthInfo() {
        doRequest(buildRequest(mModel.getAlipayAuthInfo()), 1);
    }

    public void getAlipayClient(String payInfo) {
        Observable<Map<String, String>> mapObservable = Observable.create(new ObservableOnSubscribe<Map<String, String>>() {
            @Override
            public void subscribe(ObservableEmitter<Map<String, String>> emitter) throws Exception {
                emitter.onNext(requestAlipayClient(payInfo));
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        doRequest(mapObservable, 2);
    }

    public void uploadOpenId(String id) {
        doRequest(buildRequest(mModel.uploadAlipayId(id)), 3);
    }

    public void checkBindAlipay() {
        doRequest(buildRequest(mModel.checkBindAlipay()), 4);
    }

    public void getBPower() {
        doRequest(buildRequest(mModel.getBPower()), 5);
    }

    private Map<String, String> requestAlipayClient(String payInfo) {
        AuthTask authTask = new AuthTask(mRootView.getActivityCet());
        return authTask.authV2(payInfo, true);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            getAlipayClient(((AliInfo) result.data).info);
        } else if (what == 3) {
            showToast("支付宝授权成功");
        } else if (what == 4) {
            mRootView.getIsBindAlipay(((User) result.data).is_alipay);
        } else if (what == 5) {
            mRootView.showPower(((User) result.data));
        }
    }

    @Override
    public <K> void handleOther(int what, K k) {
        if (what == 2) {
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
    }

    private void handleResults(String result) {
        //success=true&result_code=200&app_id=2018080260757000&auth_code=27cb676559524ebdb5812fc356b7XX95&scope=kuaijie&alipay_open_id=20880060896445079928843103113995&user_id=2088232345720950&target_id=52
        String[] split = result.split("&");
        for (String ss : split) {
            if (ss.contains("alipay_open_id")) {
                String[] split1 = ss.split("=");
                uploadOpenId(split1[1]);
            }
        }
    }
}
