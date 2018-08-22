package com.wta.NewCloudApp.pay;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.jess.arms.utils.ArmsUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.config.DetObsever;
import com.wta.NewCloudApp.mvp.model.entity.PayInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PayManager {
    private PayListener payListener;
    private static PayManager payManager;
    private PayManager(){}
    public static PayManager getInstance() {
        if (payManager == null)
            synchronized (PayManager.class) {
                if (payManager == null)
                    payManager = new PayManager();
            }
        return payManager;
    }

    public void requestPay(Activity activity, PayInfo payInfo, PayListener payListener) {
        this.payListener = payListener;
        if (payInfo.sign != null)
            payWithWX(payInfo);
        else
            payWithAli(activity, payInfo);
    }

    private void payWithAli(Activity activity, PayInfo payInfo) {
        Observable.create(new ObservableOnSubscribe<Map<String, String>>() {
            @Override
            public void subscribe(ObservableEmitter<Map<String, String>> emitter) throws Exception {
                emitter.onNext(getAlipayResults(activity, payInfo));
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DetObsever<Map<String, String>>() {
                    @Override
                    public void onNext(Map<String, String> map) {
                        PayResults payResult = new PayResults(map);
                        handleAliResults(payResult);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (payListener!=null) payListener.payComplete(1,-4);
                    }
                });
    }

    private void handleAliResults(PayResults payResult) {
        String resultInfo = payResult.getResult();
        String resultStatus = payResult.getResultStatus();
        if (TextUtils.equals(resultStatus, "9000")) {
            try {
                JSONObject jObj = new JSONObject(resultInfo);
                JSONObject trObj = jObj.optJSONObject("alipay_trade_app_pay_response");
                String total_amount = trObj.optString("total_amount");
                if (payListener!=null) payListener.payComplete(1,0);
            } catch (JSONException e) {//一般不会遇到此问题
                if (payListener!=null) payListener.payComplete(1,-1);
            }
        } else if (TextUtils.equals(resultStatus, "6002")) {//网络问题
            if (payListener!=null) payListener.payComplete(1,-3);
        } else if (TextUtils.equals(resultStatus, "6001")) {//支付取消
            if (payListener!=null) payListener.payComplete(1,-2);
        } else {//支付失败
            if (payListener!=null) payListener.payComplete(1,-1);
        }
    }

    private void payWithWX(PayInfo data) {
        if (!App.getInstance().getWXAPI().isWXAppInstalled()) {
            ArmsUtils.makeText(App.getInstance(), "您没有安装微信客户端，无法使用微信支付");
        } else {
            PayReq req = new PayReq();
            req.appId = data.appid;
            req.nonceStr = data.noncestr;
            req.packageValue = data.packageX;
            req.partnerId = data.partnerid;
            req.prepayId = data.prepayid;
            req.timeStamp = data.timestamp + "";
            req.sign = data.sign;
            App.getInstance().getWXAPI().sendReq(req);
        }
    }

    private Map<String, String> getAlipayResults(Activity activity, PayInfo payInfo) {
        PayTask alipay = new PayTask(activity);
        return alipay.payV2(payInfo.info, true);
    }

    public PayListener getPayListener() {
        return payListener;
    }

    public void setPayListener(PayListener payListener) {
        this.payListener = payListener;
    }
}
