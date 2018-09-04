package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.RScoreContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;


@Module
public class RScoreModule {
    private RScoreContract.View view;

    public RScoreModule(RScoreContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RScoreContract.View provideRScoreView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideRScoreModel(UserModel model) {
        return model;
    }
}