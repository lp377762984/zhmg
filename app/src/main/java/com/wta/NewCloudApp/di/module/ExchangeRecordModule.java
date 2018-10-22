package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.ExchangeRecordContract;
import com.wta.NewCloudApp.mvp.model.ExchangeRecordModel;


@Module
public class ExchangeRecordModule {
    private ExchangeRecordContract.View view;

    public ExchangeRecordModule(ExchangeRecordContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ExchangeRecordContract.View provideExchangeRecordView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ExchangeRecordContract.Model provideExchangeRecordModel(ExchangeRecordModel model) {
        return model;
    }
}