package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.BGroupListModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.BGroupListActivity;

@ActivityScope
@Component(modules = BGroupListModule.class, dependencies = AppComponent.class)
public interface BGroupListComponent {
    void inject(BGroupListActivity activity);
}