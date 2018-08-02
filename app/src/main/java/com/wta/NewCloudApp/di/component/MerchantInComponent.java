package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.MerchantInModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.MerchantInActivity;

@ActivityScope
@Component(modules = MerchantInModule.class, dependencies = AppComponent.class)
public interface MerchantInComponent {
    void inject(MerchantInActivity activity);
}