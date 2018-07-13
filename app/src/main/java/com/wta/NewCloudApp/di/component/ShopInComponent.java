package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.ShopInModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.ShopInActivity;

@ActivityScope
@Component(modules = ShopInModule.class, dependencies = AppComponent.class)
public interface ShopInComponent {
    void inject(ShopInActivity activity);
}