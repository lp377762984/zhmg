package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.RScoreModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.RScoreDetActivity;

@ActivityScope
@Component(modules = RScoreModule.class, dependencies = AppComponent.class)
public interface RScoreComponent {
    void inject(RScoreDetActivity activity);
}