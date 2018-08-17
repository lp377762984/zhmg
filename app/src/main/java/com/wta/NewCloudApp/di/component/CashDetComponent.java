package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.CashDetModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.CashDetActivity;

@ActivityScope
@Component(modules = CashDetModule.class, dependencies = AppComponent.class)
public interface CashDetComponent {
    void inject(CashDetActivity activity);
}