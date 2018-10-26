package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.ExRecDetContract;
import com.wta.NewCloudApp.mvp.model.ExRecDetModel;


@Module
public class ExRecDetModule {
    private ExRecDetContract.View view;

    /**
     * 构建ExRecDetModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ExRecDetModule(ExRecDetContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ExRecDetContract.View provideExRecDetView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ExRecDetContract.Model provideExRecDetModel(ExRecDetModel model) {
        return model;
    }
}