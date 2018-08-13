package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.SideSearchModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.SideSearchActivity;

@ActivityScope
@Component(modules = SideSearchModule.class, dependencies = AppComponent.class)
public interface SideSearchComponent {
    void inject(SideSearchActivity activity);
}