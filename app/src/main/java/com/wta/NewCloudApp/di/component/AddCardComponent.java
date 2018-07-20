package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.AddCardModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.AddCardActivity;

@ActivityScope
@Component(modules = AddCardModule.class, dependencies = AppComponent.class)
public interface AddCardComponent {
    void inject(AddCardActivity activity);
}