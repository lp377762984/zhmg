package com.wta.NewCloudApp.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.di.module.AddRecModule;
import com.wta.NewCloudApp.mvp.ui.activity.AddRecActivity;

import dagger.Component;

@ActivityScope
@Component(modules = AddRecModule.class, dependencies = AppComponent.class)
public interface AddRecComponent {
    void inject(AddRecActivity activity);
}