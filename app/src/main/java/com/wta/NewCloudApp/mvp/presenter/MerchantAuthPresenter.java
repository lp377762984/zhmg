package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.MerchantAuthContract;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;

import javax.inject.Inject;


@ActivityScope
public class MerchantAuthPresenter extends BBasePresenter<IBusinessModel, MerchantAuthContract.View> {

    @Inject
    public MerchantAuthPresenter(IBusinessModel model, MerchantAuthContract.View rootView) {
        super(model, rootView);
    }
}
