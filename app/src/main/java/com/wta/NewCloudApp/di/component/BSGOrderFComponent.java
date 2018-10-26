package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.BSGOrderFModule;

import com.jess.arms.di.scope.FragmentScope;
import com.wta.NewCloudApp.mvp.ui.fragment.BSGOrderFFragment;

@FragmentScope
@Component(modules = BSGOrderFModule.class, dependencies = AppComponent.class)
public interface BSGOrderFComponent {
    void inject(BSGOrderFFragment fragment);
}