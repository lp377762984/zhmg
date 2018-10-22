package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.SGDetModule;

import com.jess.arms.di.scope.FragmentScope;
import com.wta.NewCloudApp.mvp.ui.fragment.SGDetFragment;

@FragmentScope
@Component(modules = SGDetModule.class, dependencies = AppComponent.class)
public interface SGDetComponent {
    void inject(SGDetFragment fragment);
}