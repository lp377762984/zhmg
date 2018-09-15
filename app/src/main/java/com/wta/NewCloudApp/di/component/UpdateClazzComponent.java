package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.UpdateClazzModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.UpdateClazzActivity;

@ActivityScope
@Component(modules = UpdateClazzModule.class, dependencies = AppComponent.class)
public interface UpdateClazzComponent {
    void inject(UpdateClazzActivity activity);
}