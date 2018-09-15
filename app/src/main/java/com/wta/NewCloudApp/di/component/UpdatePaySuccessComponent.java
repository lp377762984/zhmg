package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.UpdatePaySuccessModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.UpdatePaySuccessActivity;

@ActivityScope
@Component(modules = UpdatePaySuccessModule.class, dependencies = AppComponent.class)
public interface UpdatePaySuccessComponent {
    void inject(UpdatePaySuccessActivity activity);
}