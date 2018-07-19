package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.GroupModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.GroupActivity;

@ActivityScope
@Component(modules = GroupModule.class, dependencies = AppComponent.class)
public interface GroupComponent {
    void inject(GroupActivity activity);
}