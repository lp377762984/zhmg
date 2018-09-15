package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.PayUpdateContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;

import javax.inject.Inject;


@ActivityScope
public class PayUpdatePresenter extends BBasePresenter<IUserModel, PayUpdateContract.View> {

    @Inject
    public PayUpdatePresenter(IUserModel model, PayUpdateContract.View rootView) {
        super(model, rootView);
    }
}
