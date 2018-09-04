package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.BScoreContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;


@Module
public class BScoreModule {
    private BScoreContract.View view;

    public BScoreModule(BScoreContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    BScoreContract.View provideBScoreView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideBScoreModel(UserModel model) {
        return model;
    }
}