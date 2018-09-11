package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.AboutUsContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;

import javax.inject.Inject;


@ActivityScope
public class AboutUsPresenter extends BBasePresenter<IUserModel, AboutUsContract.View> {

    @Inject
    public AboutUsPresenter(IUserModel model, AboutUsContract.View rootView) {
        super(model, rootView);
    }
}
