package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.ScoreShopModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.ScoreShopActivity;

@ActivityScope
@Component(modules = ScoreShopModule.class, dependencies = AppComponent.class)
public interface ScoreShopComponent {
    void inject(ScoreShopActivity activity);
}