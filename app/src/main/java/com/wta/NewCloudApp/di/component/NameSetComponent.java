package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.NameSetModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.NameSetActivity;

@ActivityScope
@Component(modules = NameSetModule.class, dependencies = AppComponent.class)
public interface NameSetComponent {
    void inject(NameSetActivity activity);
}