package com.wta.NewCloudApp.mvp.presenter;

import com.wta.NewCloudApp.mvp.contract.AddRecContract;
import com.wta.NewCloudApp.mvp.model.UserModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import javax.inject.Inject;


public class AddRecPresenter extends BBasePresenter<UserModel, AddRecContract.View> {


    @Inject
    public AddRecPresenter(UserModel model, AddRecContract.View rootView) {
        super(model, rootView);
    }

    public void setRecCode(String code) {
        doRequest(buildRequest(mModel.setRecCode(code)), 1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        mRootView.refresh();
    }
}
