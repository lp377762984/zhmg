package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.GetListContract;
import com.wta.NewCloudApp.mvp.model.GetListModel;


@Module
public class GetListModule {
    private GetListContract.View view;

    public GetListModule(GetListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    GetListContract.View provideGetListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    GetListContract.Model provideGetListModel(GetListModel model) {
        return model;
    }
}