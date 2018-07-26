package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.AddAddressModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.AddAddressActivity;

@ActivityScope
@Component(modules = AddAddressModule.class, dependencies = AppComponent.class)
public interface AddAddressComponent {
    void inject(AddAddressActivity activity);
}