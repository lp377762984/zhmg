package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.di.module.SweepModule;
import com.wta.NewCloudApp.mvp.ui.activity.SweepActivity;

@ActivityScope
@Component(modules = SweepModule.class, dependencies = AppComponent.class)
public interface SweepComponent {
    void inject(SweepActivity activity);
}