package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.StoreInfoModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.StoreInfoActivity;

@ActivityScope
@Component(modules = StoreInfoModule.class, dependencies = AppComponent.class)
public interface StoreInfoComponent {
    void inject(StoreInfoActivity activity);
}