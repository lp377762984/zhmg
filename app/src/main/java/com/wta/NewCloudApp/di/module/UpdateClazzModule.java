package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.UpdateClazzContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;


@Module
public class UpdateClazzModule {
    private UpdateClazzContract.View view;

    public UpdateClazzModule(UpdateClazzContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UpdateClazzContract.View provideUpdateClazzView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideUpdateClazzModel(UserModel model) {
        return model;
    }
}