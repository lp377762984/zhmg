package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.RecAwardListContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;


@Module
public class RecAwardListModule {
    private RecAwardListContract.View view;

    public RecAwardListModule(RecAwardListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RecAwardListContract.View provideRecAwardListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideRecAwardListModel(UserModel model) {
        return model;
    }
}