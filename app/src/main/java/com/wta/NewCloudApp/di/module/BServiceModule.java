package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.BServiceContract;
import com.wta.NewCloudApp.mvp.model.BServiceModel;


@Module
public class BServiceModule {
    private BServiceContract.View view;

    /**
     * 构建BServiceModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public BServiceModule(BServiceContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    BServiceContract.View provideBServiceView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    BServiceContract.Model provideBServiceModel(BServiceModel model) {
        return model;
    }
}