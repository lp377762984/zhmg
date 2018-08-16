package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.PayContract;
import com.wta.NewCloudApp.mvp.model.BusinessModel;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;

import dagger.Module;
import dagger.Provides;


@Module
public class PayModule {
    private PayContract.View view;

    public PayModule(PayContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PayContract.View providePayView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IBusinessModel providePayModel(BusinessModel model) {
        return model;
    }
}