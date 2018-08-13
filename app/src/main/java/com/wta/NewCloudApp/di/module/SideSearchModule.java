package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.SideSearchContract;
import com.wta.NewCloudApp.mvp.model.BusinessModel;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;

import dagger.Module;
import dagger.Provides;


@Module
public class SideSearchModule {
    private SideSearchContract.View view;

    public SideSearchModule(SideSearchContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SideSearchContract.View provideSideSearchView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IBusinessModel provideSideSearchModel(BusinessModel model) {
        return model;
    }
}