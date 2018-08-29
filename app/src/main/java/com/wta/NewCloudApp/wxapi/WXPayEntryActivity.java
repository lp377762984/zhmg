package com.wta.NewCloudApp.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.wxapi.pay.PayListener;
import com.wta.NewCloudApp.wxapi.pay.PayManager;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = App.getInstance().getWXAPI();
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        PayListener payListener = PayManager.getInstance().getPayListener();
        int errCode = resp.errCode;
        if (errCode == 0) {//成功
            if (payListener != null)
                payListener.payComplete(2,0);
        } else if (errCode == -1) {//错误
            if (payListener != null)
                payListener.payComplete(2,-1);
        } else if (errCode == -2) {//用户取消
            if (payListener != null)
                payListener.payComplete(2,-2);
        } else {
            if (payListener != null)
                payListener.payComplete(2,-4);
        }
        finish();
    }

}