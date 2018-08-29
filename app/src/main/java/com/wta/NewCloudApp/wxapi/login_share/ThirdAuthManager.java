package com.wta.NewCloudApp.wxapi.login_share;


import android.content.Context;

import com.jess.arms.utils.ArmsUtils;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.wta.NewCloudApp.config.App;

public class ThirdAuthManager {
    public String code;
    public AuthListener authListener;
    public static ThirdAuthManager manager;

    private ThirdAuthManager() {
    }

    public static ThirdAuthManager getInstance() {
        if (manager == null)
            synchronized (ThirdAuthManager.class) {
                if (manager == null)
                    manager = new ThirdAuthManager();
            }
        return manager;
    }

    public void requestAuth(Context context) {
        if (!App.getInstance().getWXAPI().isWXAppInstalled()) {
            ArmsUtils.makeText(context, "您没有安装微信客户端，请下载并安装微信后使用。");
            return;
        }
        authListener = new AuthListener() {
            @Override
            public void authBack(BaseResp baseResp) {
                if (baseResp.errCode==0){
                    //requestAccessToken();
                }
            }
        };
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "qlqw";
        App.getInstance().getWXAPI().sendReq(req);
    }
}
