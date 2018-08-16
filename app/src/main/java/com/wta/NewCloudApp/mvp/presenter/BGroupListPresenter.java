package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.BGroupListContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;

import javax.inject.Inject;


@ActivityScope
public class BGroupListPresenter extends BBasePresenter<IUserModel, BGroupListContract.View> {

    @Inject
    public BGroupListPresenter(IUserModel model, BGroupListContract.View rootView) {
        super(model, rootView);
    }
}
