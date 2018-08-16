package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.BQRModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.BQRActivity;

@ActivityScope
@Component(modules = BQRModule.class, dependencies = AppComponent.class)
public interface BQRComponent {
    void inject(BQRActivity activity);
}