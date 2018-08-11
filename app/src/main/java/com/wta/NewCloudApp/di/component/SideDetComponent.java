package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.SideDetModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.SideDetActivity;

@ActivityScope
@Component(modules = SideDetModule.class, dependencies = AppComponent.class)
public interface SideDetComponent {
    void inject(SideDetActivity activity);
}