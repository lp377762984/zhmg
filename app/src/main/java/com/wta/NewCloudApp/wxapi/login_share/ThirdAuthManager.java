package com.wta.NewCloudApp.wxapi.login_share;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.jess.arms.utils.ArmsUtils;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.config.DetSubscriber;
import com.wta.NewCloudApp.mvp.model.entity.WXUserInfo;
import com.wta.NewCloudApp.mvp.model.api.HttpServices;
import com.wta.NewCloudApp.mvp.model.entity.WXAccessToken;
import com.wta.NewCloudApp.uitls.FinalUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ThirdAuthManager {
    public AuthListener authListener;

    private WXOpenIdListener openIdListener;

    private static ThirdAuthManager manager;
    private Context context;

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

    public void requestAuth(Context context, WXOpenIdListener listener) {
        if (!App.getInstance().getWXAPI().isWXAppInstalled()) {
            ArmsUtils.makeText(context, "您没有安装微信客户端，请下载并安装微信后使用。");
            return;
        }
        this.context = context;
        openIdListener = listener;
        authListener = new AuthListener() {
            @Override
            public void authBack(BaseResp baseResp) {
                int errCode = baseResp.errCode;
                if (baseResp.errCode == 0) {
                    requestAccessToken((((SendAuth.Resp) baseResp).code));
                } else if (errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
                    ArmsUtils.makeText(context, "微信登录已取消");
                } else if (errCode == BaseResp.ErrCode.ERR_SENT_FAILED) {
                    ArmsUtils.makeText(context, "微信登录失败");
                } else if (errCode == BaseResp.ErrCode.ERR_AUTH_DENIED) {
                    ArmsUtils.makeText(context, "您拒绝了微信授权");
                } else if (errCode == BaseResp.ErrCode.ERR_UNSUPPORT) {
                    Log.e("wx", "onResp: 不支持！");
                } else if (errCode == BaseResp.ErrCode.ERR_BAN) {
                    Log.e("wx", "onResp: 签名不匹配！");
                }
            }
        };
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "zhmg";
        App.getInstance().getWXAPI().sendReq(req);
    }

    private void requestAccessToken(String code) {
        ArmsUtils.obtainAppComponentFromContext(context).repositoryManager().obtainRetrofitService(HttpServices.class)
                .getAccessToken(FinalUtils.WX_APP_ID, FinalUtils.WX_APP_SECRET, code, "authorization_code")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DetSubscriber<WXAccessToken>() {
                    @Override
                    public void onNext(WXAccessToken wxAccessToken) {
                        if (!TextUtils.isEmpty(wxAccessToken.access_token)) {
                            openIdListener.showWXOpenId(wxAccessToken);
                        } else {
                            ArmsUtils.makeText(context.getApplicationContext(), "access_token获取失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ArmsUtils.makeText(context.getApplicationContext(), "遇到错误，access_token获取失败");
                    }
                });
    }

    public void requestWXUserInfo(String accessToken, String openid, WXUserListener wxUserListener) {
        ArmsUtils.obtainAppComponentFromContext(context).repositoryManager().obtainRetrofitService(HttpServices.class)
                .getWXUserInfo(accessToken, openid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DetSubscriber<WXUserInfo>() {
                    @Override
                    public void onNext(WXUserInfo wxUserInfo) {
                        if (!TextUtils.isEmpty(wxUserInfo.openid)) {
                            wxUserListener.showWXUser(wxUserInfo);
                        } else {
                            ArmsUtils.makeText(context.getApplicationContext(), "微信用户信息获取失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ArmsUtils.makeText(context.getApplicationContext(), "遇到错误，微信用户信息获取失败");
                    }
                });
    }
}
