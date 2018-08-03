package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.MerchantInfoContract;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;

import javax.inject.Inject;


@ActivityScope
public class MerchantInfoPresenter extends BBasePresenter<IBusinessModel, MerchantInfoContract.View> {

    @Inject
    public MerchantInfoPresenter(IBusinessModel model, MerchantInfoContract.View rootView) {
        super(model, rootView);
    }
}
