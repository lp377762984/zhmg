package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.SideModule;

import com.jess.arms.di.scope.FragmentScope;
import com.wta.NewCloudApp.mvp.ui.fragment.SideFragment;

@FragmentScope
@Component(modules = SideModule.class, dependencies = AppComponent.class)
public interface SideComponent {
    void inject(SideFragment fragment);
}