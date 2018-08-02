package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.MerchantAuthModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.MerchantAuthActivity;

@ActivityScope
@Component(modules = MerchantAuthModule.class, dependencies = AppComponent.class)
public interface MerchantAuthComponent {
    void inject(MerchantAuthActivity activity);
}