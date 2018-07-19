package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.GroupContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;


@Module
public class GroupModule {
    private GroupContract.View view;

    public GroupModule(GroupContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    GroupContract.View provideGroupView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideGroupModel(UserModel model) {
        return model;
    }
}