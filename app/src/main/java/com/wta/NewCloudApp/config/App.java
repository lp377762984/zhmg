package com.wta.NewCloudApp.config;

import com.jess.arms.base.BaseApplication;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wta.NewCloudApp.uitls.FinalUtils;


public class App extends BaseApplication {
    private static App app;
    private IWXAPI iwxapi;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static App getInstance() {
        return app;
    }

    public IWXAPI getWXAPI() {
        if (iwxapi == null) {
            iwxapi = WXAPIFactory.createWXAPI(this, null);
            iwxapi.registerApp(FinalUtils.WX_APP_ID);
        }
        return iwxapi;
    }
}
