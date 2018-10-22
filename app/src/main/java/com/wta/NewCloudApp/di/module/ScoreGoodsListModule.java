package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.ScoreGoodsListContract;
import com.wta.NewCloudApp.mvp.model.ScoreGoodsListModel;


@Module
public class ScoreGoodsListModule {
    private ScoreGoodsListContract.View view;

    public ScoreGoodsListModule(ScoreGoodsListContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    ScoreGoodsListContract.View provideScoreGoodsListView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    ScoreGoodsListContract.Model provideScoreGoodsListModel(ScoreGoodsListModel model) {
        return model;
    }
}