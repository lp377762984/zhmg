package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.BRecordModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.BRecordActivity;

@ActivityScope
@Component(modules = BRecordModule.class, dependencies = AppComponent.class)
public interface BRecordComponent {
    void inject(BRecordActivity activity);
}