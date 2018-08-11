package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.SideDetContract;
import com.wta.NewCloudApp.mvp.model.BusinessModel;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;


@Module
public class SideDetModule {
    private SideDetContract.View view;

    public SideDetModule(SideDetContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SideDetContract.View provideSideDetView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IBusinessModel provideSideDetModel(BusinessModel model) {
        return model;
    }
}