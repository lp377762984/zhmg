package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.BServiceModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.BServiceActivity;

@ActivityScope
@Component(modules = BServiceModule.class, dependencies = AppComponent.class)
public interface BServiceComponent {
    void inject(BServiceActivity activity);
}