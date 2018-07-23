package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.BindPhoneContract;
import com.wta.NewCloudApp.mvp.model.UserModel;
import com.wta.NewCloudApp.mvp.model.entity.LoginEntity;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.User;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


@ActivityScope
public class BindPhonePresenter extends BBasePresenter<UserModel, BindPhoneContract.View> {


    @Inject
    public BindPhonePresenter(UserModel model, BindPhoneContract.View rootView) {
        super(model, rootView);
    }

    public void sendCode(String mobile){
        doRequest(buildListRequest(mModel.bindSendCode(mobile)),1);
    }

    public void bindPhone(String mobile, String verify, Map<String,String> map){
        doRequest(buildRequest(mModel.bindPhone(mobile,verify,map)),2);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what==1){
            mRootView.sendCode((Result<User>) result);
        }else if (what==2){
            mRootView.bindPhone((Result<LoginEntity>) result);
        }
    }
}
