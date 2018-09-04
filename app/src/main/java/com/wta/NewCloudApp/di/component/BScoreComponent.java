package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.BScoreModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.BScoreDetActivity;

@ActivityScope
@Component(modules = BScoreModule.class, dependencies = AppComponent.class)
public interface BScoreComponent {
    void inject(BScoreDetActivity activity);
}