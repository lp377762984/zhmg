package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.BindPhoneModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.BindPhoneActivity;

@ActivityScope
@Component(modules = BindPhoneModule.class, dependencies = AppComponent.class)
public interface BindPhoneComponent {
    void inject(BindPhoneActivity activity);
}