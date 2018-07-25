package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.NameSetContract;
import com.wta.NewCloudApp.mvp.model.UserModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.User;

import java.io.File;

import javax.inject.Inject;

@ActivityScope
public class NameSetPresenter extends BBasePresenter<UserModel, NameSetContract.View> {
    @Inject
    public NameSetPresenter(UserModel model, NameSetContract.View rootView) {
        super(model, rootView);
    }

    public void setNickName(String nickName, File head, int type) {
        doRequest(buildRequest(mModel.setUser(nickName, head)), type);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        mRootView.showName(((User) result.data).nickname);
    }
}
