package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.ExtraRecordFModule;

import com.jess.arms.di.scope.FragmentScope;
import com.wta.NewCloudApp.mvp.ui.fragment.ExtraRecordFFragment;

@FragmentScope
@Component(modules = ExtraRecordFModule.class, dependencies = AppComponent.class)
public interface ExtraRecordFComponent {
    void inject(ExtraRecordFFragment fragment);
}