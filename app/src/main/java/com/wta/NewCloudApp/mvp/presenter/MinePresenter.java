package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.MineContract;
import com.wta.NewCloudApp.mvp.model.UserModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.Share;

import javax.inject.Inject;


@ActivityScope
public class MinePresenter extends BBasePresenter<UserModel, MineContract.View> {

    @Inject
    public MinePresenter(UserModel model, MineContract.View rootView) {
        super(model, rootView);
    }

    public void startShare(){
        doRequest(buildRequest(mModel.getShare()),0);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what==0){
            mRootView.share((Result<Share>) result);
        }
    }
}
