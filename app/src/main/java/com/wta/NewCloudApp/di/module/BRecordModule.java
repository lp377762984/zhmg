package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.BRecordContract;
import com.wta.NewCloudApp.mvp.model.BusinessModel;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;

import dagger.Module;
import dagger.Provides;


@Module
public class BRecordModule {
    private BRecordContract.View view;

    public BRecordModule(BRecordContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    BRecordContract.View provideBRecordView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IBusinessModel provideBRecordModel(BusinessModel model) {
        return model;
    }
}