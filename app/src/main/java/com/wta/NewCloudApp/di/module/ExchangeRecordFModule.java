package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.ExchangeRecordFContract;
import com.wta.NewCloudApp.mvp.model.ExchangeRecordFModel;


@Module
public class ExchangeRecordFModule {
    private ExchangeRecordFContract.View view;

    public ExchangeRecordFModule(ExchangeRecordFContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    ExchangeRecordFContract.View provideExchangeRecordFView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    ExchangeRecordFContract.Model provideExchangeRecordFModel(ExchangeRecordFModel model) {
        return model;
    }
}