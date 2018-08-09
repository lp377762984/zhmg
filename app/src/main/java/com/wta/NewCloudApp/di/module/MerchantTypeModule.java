package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.MerchantTypeContract;
import com.wta.NewCloudApp.mvp.model.BusinessModel;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;

import dagger.Module;
import dagger.Provides;


@Module
public class MerchantTypeModule {
    private MerchantTypeContract.View view;

    public MerchantTypeModule(MerchantTypeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MerchantTypeContract.View provideMerchantTypeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IBusinessModel provideMerchantTypeModel(BusinessModel model) {
        return model;
    }
}