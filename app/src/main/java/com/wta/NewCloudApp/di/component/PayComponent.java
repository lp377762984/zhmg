package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.PayModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.PayActivity;

@ActivityScope
@Component(modules = PayModule.class, dependencies = AppComponent.class)
public interface PayComponent {
    void inject(PayActivity activity);
}