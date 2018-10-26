package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.SGDetBtmModule;

import com.jess.arms.di.scope.FragmentScope;
import com.wta.NewCloudApp.mvp.ui.fragment.SGDetBtmFragment;

@FragmentScope
@Component(modules = SGDetBtmModule.class, dependencies = AppComponent.class)
public interface SGDetBtmComponent {
    void inject(SGDetBtmFragment fragment);
}