package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.ExtraRecordFContract;
import com.wta.NewCloudApp.mvp.model.ExtraRecordFModel;


@Module
public class ExtraRecordFModule {
    private ExtraRecordFContract.View view;

    public ExtraRecordFModule(ExtraRecordFContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    ExtraRecordFContract.View provideExtraRecordFView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    ExtraRecordFContract.Model provideExtraRecordFModel(ExtraRecordFModel model) {
        return model;
    }
}