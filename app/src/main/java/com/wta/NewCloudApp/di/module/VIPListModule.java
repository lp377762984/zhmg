package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.VIPListContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;


@Module
public class VIPListModule {
    private VIPListContract.View view;

    public VIPListModule(VIPListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    VIPListContract.View provideVIPListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideVIPListModel(UserModel model) {
        return model;
    }
}