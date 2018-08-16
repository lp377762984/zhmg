package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.CashGetModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.CashGetMActivity;

@ActivityScope
@Component(modules = CashGetModule.class, dependencies = AppComponent.class)
public interface CashGetComponent {
    void inject(CashGetMActivity activity);
}