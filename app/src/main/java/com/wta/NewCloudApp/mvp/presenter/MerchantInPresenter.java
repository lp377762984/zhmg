package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.MerchantInContract;
import com.wta.NewCloudApp.mvp.model.BusinessModel;

import javax.inject.Inject;


@ActivityScope
public class MerchantInPresenter extends BBasePresenter<BusinessModel, MerchantInContract.View> {

    @Inject
    public MerchantInPresenter(BusinessModel model, MerchantInContract.View rootView) {
        super(model, rootView);
    }

}
