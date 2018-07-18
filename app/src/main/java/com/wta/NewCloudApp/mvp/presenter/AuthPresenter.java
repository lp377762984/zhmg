package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.AuthContract;
import com.wta.NewCloudApp.mvp.model.UserModel;

import javax.inject.Inject;


@ActivityScope
public class AuthPresenter extends BBasePresenter<UserModel, AuthContract.View> {


    @Inject
    public AuthPresenter(UserModel model, AuthContract.View rootView) {
        super(model, rootView);
    }

}
