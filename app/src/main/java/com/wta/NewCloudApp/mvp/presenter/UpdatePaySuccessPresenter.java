package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.UpdatePaySuccessContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;

import javax.inject.Inject;


@ActivityScope
public class UpdatePaySuccessPresenter extends BBasePresenter<IUserModel, UpdatePaySuccessContract.View> {

    @Inject
    public UpdatePaySuccessPresenter(IUserModel model, UpdatePaySuccessContract.View rootView) {
        super(model, rootView);
    }
}
