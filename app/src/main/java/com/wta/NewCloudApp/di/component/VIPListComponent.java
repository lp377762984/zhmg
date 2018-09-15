package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.VIPListModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.VIPListActivity;

@ActivityScope
@Component(modules = VIPListModule.class, dependencies = AppComponent.class)
public interface VIPListComponent {
    void inject(VIPListActivity activity);
}