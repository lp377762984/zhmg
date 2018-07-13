package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.UserQRModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.UserQRActivity;

@ActivityScope
@Component(modules = UserQRModule.class, dependencies = AppComponent.class)
public interface UserQRComponent {
    void inject(UserQRActivity activity);
}