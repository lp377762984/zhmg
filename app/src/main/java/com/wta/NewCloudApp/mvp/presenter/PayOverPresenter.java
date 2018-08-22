package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.wta.NewCloudApp.mvp.contract.PayOverContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;


@ActivityScope
public class PayOverPresenter extends BBasePresenter<IUserModel, PayOverContract.View> {

    @Inject
    public PayOverPresenter(IUserModel model, PayOverContract.View rootView) {
        super(model, rootView);
    }
}
