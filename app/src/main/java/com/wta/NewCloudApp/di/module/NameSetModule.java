package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.NameSetContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;


@Module
public class NameSetModule {
    private NameSetContract.View view;

    public NameSetModule(NameSetContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    NameSetContract.View provideNameSetView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideNameSetModel(UserModel model) {
        return model;
    }
}