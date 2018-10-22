package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.ExchangeRecordFModule;

import com.jess.arms.di.scope.FragmentScope;
import com.wta.NewCloudApp.mvp.ui.fragment.ExchangeRecordFFragment;

@FragmentScope
@Component(modules = ExchangeRecordFModule.class, dependencies = AppComponent.class)
public interface ExchangeRecordFComponent {
    void inject(ExchangeRecordFFragment fragment);
}