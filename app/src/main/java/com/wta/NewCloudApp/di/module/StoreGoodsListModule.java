package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.StoreGoodsListContract;
import com.wta.NewCloudApp.mvp.model.StoreGoodsListModel;


@Module
public class StoreGoodsListModule {
    private StoreGoodsListContract.View view;

    public StoreGoodsListModule(StoreGoodsListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    StoreGoodsListContract.View provideStoreGoodsListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    StoreGoodsListContract.Model provideStoreGoodsListModel(StoreGoodsListModel model) {
        return model;
    }
}