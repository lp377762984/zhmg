package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.MerchantInfoModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.MerchantInfoActivity;

@ActivityScope
@Component(modules = MerchantInfoModule.class, dependencies = AppComponent.class)
public interface MerchantInfoComponent {
    void inject(MerchantInfoActivity activity);
}