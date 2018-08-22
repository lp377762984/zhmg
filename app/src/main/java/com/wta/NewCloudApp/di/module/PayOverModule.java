package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.PayOverContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;


@Module
public class PayOverModule {
    private PayOverContract.View view;

    public PayOverModule(PayOverContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PayOverContract.View providePayOverView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel providePayOverModel(UserModel model) {
        return model;
    }
}