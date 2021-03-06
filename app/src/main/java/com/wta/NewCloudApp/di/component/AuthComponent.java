package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.AuthModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.AuthActivity;

@ActivityScope
@Component(modules = AuthModule.class, dependencies = AppComponent.class)
public interface AuthComponent {
    void inject(AuthActivity activity);
}