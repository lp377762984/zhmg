package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.wta.NewCloudApp.mvp.contract.UserQRContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.Share;

import javax.inject.Inject;


@ActivityScope
public class UserQRPresenter extends BBasePresenter<IUserModel, UserQRContract.View> {
    @Inject
    public UserQRPresenter(IUserModel model, UserQRContract.View rootView) {
        super(model, rootView);
    }
    public void startShare(){
        doRequest(buildRequest(mModel.getShare()),0);
    }

    public void shareBigImage() {
        doRequest(buildRequest(mModel.shareBigImage()),1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what==0){
            mRootView.share((Result<Share>) result);
        }else if (what==1){
            mRootView.share((Result<Share>) result);
        }
    }
}
