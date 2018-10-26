package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.ExRecDetModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.ExRecDetActivity;

@ActivityScope
@Component(modules = ExRecDetModule.class, dependencies = AppComponent.class)
public interface ExRecDetComponent {
    void inject(ExRecDetActivity activity);
}