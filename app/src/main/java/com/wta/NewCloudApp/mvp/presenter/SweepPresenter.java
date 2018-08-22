package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.SweepContract;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;

import javax.inject.Inject;

@ActivityScope
public class SweepPresenter extends BBasePresenter<IBusinessModel,SweepContract.View> {

    @Inject
    public SweepPresenter(IBusinessModel model, SweepContract.View rootView) {
        super(model, rootView);
    }
}
