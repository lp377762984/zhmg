package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.PayOverModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.PayOverActivity;

@ActivityScope
@Component(modules = PayOverModule.class, dependencies = AppComponent.class)
public interface PayOverComponent {
    void inject(PayOverActivity activity);
}