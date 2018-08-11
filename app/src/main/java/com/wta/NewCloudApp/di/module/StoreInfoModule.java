package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.StoreInfoContract;
import com.wta.NewCloudApp.mvp.model.BusinessModel;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;

import dagger.Module;
import dagger.Provides;


@Module
public class StoreInfoModule {
    private StoreInfoContract.View view;

    public StoreInfoModule(StoreInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    StoreInfoContract.View provideStoreInfoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IBusinessModel provideStoreInfoModel(BusinessModel model) {
        return model;
    }
}