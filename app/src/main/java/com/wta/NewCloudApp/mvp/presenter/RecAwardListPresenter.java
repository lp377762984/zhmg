package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.RecAwardListContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;

import javax.inject.Inject;


@ActivityScope
public class RecAwardListPresenter extends BBasePresenter<IUserModel, RecAwardListContract.View> {

    @Inject
    public RecAwardListPresenter(IUserModel model, RecAwardListContract.View rootView) {
        super(model, rootView);
    }
}
