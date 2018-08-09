package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.MerchantTypeModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.MerchantTypeActivity;

@ActivityScope
@Component(modules = MerchantTypeModule.class, dependencies = AppComponent.class)
public interface MerchantTypeComponent {
    void inject(MerchantTypeActivity activity);
}