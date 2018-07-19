package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.BindPhoneContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;


@Module
public class BindPhoneModule {
    private BindPhoneContract.View view;

    public BindPhoneModule(BindPhoneContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    BindPhoneContract.View provideBindPhoneView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideBindPhoneModel(UserModel model) {
        return model;
    }
}