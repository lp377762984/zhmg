package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.LoginContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.entity.WXUserInfo;
import com.wta.NewCloudApp.mvp.model.entity.LoginEntity;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.User;

import javax.inject.Inject;


@ActivityScope
public class LoginPresenter extends BBasePresenter<IUserModel, LoginContract.View> {

    @Inject
    public LoginPresenter(IUserModel model, LoginContract.View rootView) {
        super(model, rootView);
    }

    public void getCode(String phone) {
        doRequest(buildRequest(mModel.sendCode(phone)), 1);
    }

    public void login(String phone, String code, String recCode) {
        doRequest(buildRequest(mModel.login(phone, code, recCode)), 3);
    }

    public void wxLogin(String openID) {
        doRequest(buildRequest(mModel.wxLogin(openID)), 2);
    }

    public void bindPhoneLogin(String mobile, String verify, WXUserInfo info){
        doRequest(buildRequest(mModel.bindPhone(mobile,verify,info)),4);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            mRootView.timeCutDown((Result<User>) result);
        } else {
            mRootView.loginSuccess((Result<LoginEntity>) result);
        }
    }

    /**
     * 解决重复收到事件的问题
     */
    @Override
    public boolean useEventBus() {
        return false;
    }
}
