package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.AddressListModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.AddressListActivity;

@ActivityScope
@Component(modules = AddressListModule.class, dependencies = AppComponent.class)
public interface AddressListComponent {
    void inject(AddressListActivity activity);
}