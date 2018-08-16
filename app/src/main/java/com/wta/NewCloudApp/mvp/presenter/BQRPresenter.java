package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.BQRContract;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;

import javax.inject.Inject;


@ActivityScope
public class BQRPresenter extends BBasePresenter<IBusinessModel, BQRContract.View> {

    @Inject
    public BQRPresenter(IBusinessModel model, BQRContract.View rootView) {
        super(model, rootView);
    }
}
