package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.ScoreShopContract;
import com.wta.NewCloudApp.mvp.model.ScoreShopModel;


@Module
public class ScoreShopModule {
    private ScoreShopContract.View view;

    public ScoreShopModule(ScoreShopContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ScoreShopContract.View provideScoreShopView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ScoreShopContract.Model provideScoreShopModel(ScoreShopModel model) {
        return model;
    }
}