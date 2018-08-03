package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.MerchantInfoContract;
import com.wta.NewCloudApp.mvp.model.BusinessModel;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;


@Module
public class MerchantInfoModule {
    private MerchantInfoContract.View view;

    public MerchantInfoModule(MerchantInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MerchantInfoContract.View provideMerchantInfoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IBusinessModel provideMerchantInfoModel(BusinessModel model) {
        return model;
    }
}