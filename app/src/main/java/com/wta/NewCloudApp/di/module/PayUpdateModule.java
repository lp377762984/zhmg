package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.PayUpdateContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;


@Module
public class PayUpdateModule {
    private PayUpdateContract.View view;

    public PayUpdateModule(PayUpdateContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PayUpdateContract.View providePayUpdateView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel providePayUpdateModel(UserModel model) {
        return model;
    }
}