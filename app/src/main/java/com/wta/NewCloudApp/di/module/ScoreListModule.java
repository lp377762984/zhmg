package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.ScoreListContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;


@Module
public class ScoreListModule {
    private ScoreListContract.View view;

    public ScoreListModule(ScoreListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ScoreListContract.View provideScoreListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideScoreListModel(UserModel model) {
        return model;
    }
}