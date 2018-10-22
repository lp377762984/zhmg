package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.SGDetContract;
import com.wta.NewCloudApp.mvp.model.SGDetModel;


@Module
public class SGDetModule {
    private SGDetContract.View view;

    public SGDetModule(SGDetContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    SGDetContract.View provideSGDetView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    SGDetContract.Model provideSGDetModel(SGDetModel model) {
        return model;
    }
}