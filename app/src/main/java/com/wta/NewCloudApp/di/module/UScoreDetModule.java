package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.UScoreDetContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;


@Module
public class UScoreDetModule {
    private UScoreDetContract.View view;

    public UScoreDetModule(UScoreDetContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UScoreDetContract.View provideUScoreDetView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideUScoreDetModel(UserModel model) {
        return model;
    }
}