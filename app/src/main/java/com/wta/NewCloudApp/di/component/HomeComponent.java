package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.HomeModule;

import com.jess.arms.di.scope.FragmentScope;
import com.wta.NewCloudApp.mvp.ui.fragment.HomeFragment;

@FragmentScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeFragment fragment);
}