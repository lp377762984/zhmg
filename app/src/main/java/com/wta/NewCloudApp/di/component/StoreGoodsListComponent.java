package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.StoreGoodsListModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.StoreGoodsListActivity;

@ActivityScope
@Component(modules = StoreGoodsListModule.class, dependencies = AppComponent.class)
public interface StoreGoodsListComponent {
    void inject(StoreGoodsListActivity activity);
}