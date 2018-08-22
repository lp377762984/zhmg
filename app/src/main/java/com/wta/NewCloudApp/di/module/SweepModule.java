package com.wta.NewCloudApp.di.module;


import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.SweepContract;
import com.wta.NewCloudApp.mvp.model.BusinessModel;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;

import dagger.Module;
import dagger.Provides;


@Module
public class SweepModule {
    private SweepContract.View view;

    public SweepModule(SweepContract.View view) {
        this.view = view;
    }
    @ActivityScope
    @Provides
    SweepContract.View provideSweepView() {
        return this.view;
    }
    @ActivityScope
    @Provides
    IBusinessModel provideSweepModel(BusinessModel model) {
        return model;
    }
}