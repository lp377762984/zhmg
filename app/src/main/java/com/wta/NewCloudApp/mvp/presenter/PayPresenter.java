package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.PayContract;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;

import javax.inject.Inject;


@ActivityScope
public class PayPresenter extends BBasePresenter<IBusinessModel, PayContract.View> {

    @Inject
    public PayPresenter(IBusinessModel model, PayContract.View rootView) {
        super(model, rootView);
    }

}
