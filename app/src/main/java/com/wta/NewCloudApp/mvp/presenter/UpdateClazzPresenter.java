package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.wta.NewCloudApp.mvp.contract.UpdateClazzContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;


@ActivityScope
public class UpdateClazzPresenter extends BBasePresenter<IUserModel, UpdateClazzContract.View> {

    @Inject
    public UpdateClazzPresenter(IUserModel model, UpdateClazzContract.View rootView) {
        super(model, rootView);
    }
}
