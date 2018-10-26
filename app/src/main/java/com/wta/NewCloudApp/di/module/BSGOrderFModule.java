package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.BSGOrderFContract;
import com.wta.NewCloudApp.mvp.model.BSGOrderFModel;


@Module
public class BSGOrderFModule {
    private BSGOrderFContract.View view;

    /**
     * 构建BSGOrderFModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public BSGOrderFModule(BSGOrderFContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    BSGOrderFContract.View provideBSGOrderFView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    BSGOrderFContract.Model provideBSGOrderFModel(BSGOrderFModel model) {
        return model;
    }
}