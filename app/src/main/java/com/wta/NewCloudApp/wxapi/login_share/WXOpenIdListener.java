package com.wta.NewCloudApp.wxapi.login_share;

import com.wta.NewCloudApp.mvp.model.entity.WXAccessToken;

public interface WXOpenIdListener {
    void showWXOpenId(WXAccessToken wxAccessToken);
}
