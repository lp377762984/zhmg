package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.CardListModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.CardListActivity;

@ActivityScope
@Component(modules = CardListModule.class, dependencies = AppComponent.class)
public interface CardListComponent {
    void inject(CardListActivity activity);
}