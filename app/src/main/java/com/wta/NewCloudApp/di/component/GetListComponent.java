package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.GetListModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.GetListActivity;

@ActivityScope
@Component(modules = GetListModule.class, dependencies = AppComponent.class)
public interface GetListComponent {
    void inject(GetListActivity activity);
}