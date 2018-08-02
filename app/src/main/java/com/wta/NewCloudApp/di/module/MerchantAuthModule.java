package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.MerchantAuthContract;
import com.wta.NewCloudApp.mvp.model.BusinessModel;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;

import dagger.Module;
import dagger.Provides;


@Module
public class MerchantAuthModule {
    private MerchantAuthContract.View view;

    public MerchantAuthModule(MerchantAuthContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MerchantAuthContract.View provideMerchantAuthView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IBusinessModel provideMerchantAuthModel(BusinessModel model) {
        return model;
    }
}