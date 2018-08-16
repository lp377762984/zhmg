package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.BGroupListContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;


@Module
public class BGroupListModule {
    private BGroupListContract.View view;

    public BGroupListModule(BGroupListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    BGroupListContract.View provideBGroupListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideBGroupListModel(UserModel model) {
        return model;
    }
}