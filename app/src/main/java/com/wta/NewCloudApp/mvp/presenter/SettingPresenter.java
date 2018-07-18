package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.SettingContract;
import com.wta.NewCloudApp.mvp.model.UserModel;

import javax.inject.Inject;


@ActivityScope
public class SettingPresenter extends BBasePresenter<UserModel, SettingContract.View> {

    @Inject
    public SettingPresenter(UserModel model, SettingContract.View rootView) {
        super(model, rootView);
    }
}
