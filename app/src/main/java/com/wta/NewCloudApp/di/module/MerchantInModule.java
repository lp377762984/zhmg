package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.MerchantInContract;
import com.wta.NewCloudApp.mvp.model.BusinessModel;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;

import dagger.Module;
import dagger.Provides;


@Module
public class MerchantInModule {
    private MerchantInContract.View view;

    public MerchantInModule(MerchantInContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MerchantInContract.View provideMerchantInView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IBusinessModel provideMerchantInModel(BusinessModel model) {
        return model;
    }
}