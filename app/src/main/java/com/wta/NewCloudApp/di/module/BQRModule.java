package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.BQRContract;
import com.wta.NewCloudApp.mvp.model.BusinessModel;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;

import dagger.Module;
import dagger.Provides;


@Module
public class BQRModule {
    private BQRContract.View view;

    public BQRModule(BQRContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    BQRContract.View provideBQRView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IBusinessModel provideBQRModel(BusinessModel model) {
        return model;
    }
}