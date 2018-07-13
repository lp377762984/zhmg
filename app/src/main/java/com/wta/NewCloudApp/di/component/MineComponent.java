package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.MineModule;

import com.jess.arms.di.scope.FragmentScope;
import com.wta.NewCloudApp.mvp.ui.fragment.MineFragment;

@FragmentScope
@Component(modules = MineModule.class, dependencies = AppComponent.class)
public interface MineComponent {
    void inject(MineFragment fragment);
}