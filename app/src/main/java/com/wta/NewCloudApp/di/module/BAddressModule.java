package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.model.BusinessModel;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;

import dagger.Module;
import dagger.Provides;
@Module
public class BAddressModule {
    @ActivityScope
    @Provides
    IBusinessModel provideBAddressModel(BusinessModel model) {
        return model;
    }
}
