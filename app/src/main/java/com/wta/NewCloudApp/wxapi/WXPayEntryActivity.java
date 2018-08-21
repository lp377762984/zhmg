package com.wta.NewCloudApp.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jess.arms.utils.ArmsUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.config.App;

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
        int errCode = resp.errCode;
        if (errCode == 0) {//成功
            ArmsUtils.makeText(this, "微信支付成功");
        } else if (errCode == -1) {//错误
            ArmsUtils.makeText(this, getString(R.string.failed_pay));
        } else if (errCode == -2) {//用户取消
            ArmsUtils.makeText(this, getString(R.string.cancel_pay));
        }
    }

}