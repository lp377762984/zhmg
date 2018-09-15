package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.PayUpdateModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.PayUpdateActivity;

@ActivityScope
@Component(modules = PayUpdateModule.class, dependencies = AppComponent.class)
public interface PayUpdateComponent {
    void inject(PayUpdateActivity activity);
}