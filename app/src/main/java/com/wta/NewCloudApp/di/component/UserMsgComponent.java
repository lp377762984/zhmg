package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.UserMsgModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.UserMsgActivity;

@ActivityScope
@Component(modules = UserMsgModule.class, dependencies = AppComponent.class)
public interface UserMsgComponent {
    void inject(UserMsgActivity activity);
}