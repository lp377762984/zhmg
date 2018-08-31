package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.BRecordContract;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;

import javax.inject.Inject;


@ActivityScope
public class BRecordPresenter extends BBasePresenter<IBusinessModel, BRecordContract.View> {

    @Inject
    public BRecordPresenter(IBusinessModel model, BRecordContract.View rootView) {
        super(model, rootView);
    }

}
