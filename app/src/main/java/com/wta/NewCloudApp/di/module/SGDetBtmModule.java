package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.SGDetBtmContract;
import com.wta.NewCloudApp.mvp.model.SGDetBtmModel;


@Module
public class SGDetBtmModule {
    private SGDetBtmContract.View view;

    /**
     * 构建SGDetBtmModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SGDetBtmModule(SGDetBtmContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    SGDetBtmContract.View provideSGDetBtmView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    SGDetBtmContract.Model provideSGDetBtmModel(SGDetBtmModel model) {
        return model;
    }
}