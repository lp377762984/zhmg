package com.wta.NewCloudApp.wxapi.login_share;

import com.tencent.mm.opensdk.modelbase.BaseResp;

public interface AuthListener {
    void authBack(BaseResp baseResp);
}
