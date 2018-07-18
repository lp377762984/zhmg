package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.MsgModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.MsgActivity;

@ActivityScope
@Component(modules = MsgModule.class, dependencies = AppComponent.class)
public interface MsgComponent {
    void inject(MsgActivity activity);
}