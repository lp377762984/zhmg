package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.AboutUsContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;


@Module
public class AboutUsModule {
    private AboutUsContract.View view;

    public AboutUsModule(AboutUsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    AboutUsContract.View provideAboutUsView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideAboutUsModel(UserModel model) {
        return model;
    }
}