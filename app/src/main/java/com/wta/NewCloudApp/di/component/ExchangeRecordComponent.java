package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.ExchangeRecordModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.ExchangeRecordActivity;

@ActivityScope
@Component(modules = ExchangeRecordModule.class, dependencies = AppComponent.class)
public interface ExchangeRecordComponent {
    void inject(ExchangeRecordActivity activity);
}