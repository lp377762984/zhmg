package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.RecAwardListModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.RecAwardListActivity;

@ActivityScope
@Component(modules = RecAwardListModule.class, dependencies = AppComponent.class)
public interface RecAwardListComponent {
    void inject(RecAwardListActivity activity);
}