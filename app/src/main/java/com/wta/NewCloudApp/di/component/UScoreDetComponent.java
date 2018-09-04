package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.UScoreDetModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.UScoreDetActivity;

@ActivityScope
@Component(modules = UScoreDetModule.class, dependencies = AppComponent.class)
public interface UScoreDetComponent {
    void inject(UScoreDetActivity activity);
}