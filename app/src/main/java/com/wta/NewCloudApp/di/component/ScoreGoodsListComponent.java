package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.ScoreGoodsListModule;

import com.jess.arms.di.scope.FragmentScope;
import com.wta.NewCloudApp.mvp.ui.fragment.ScoreGoodsListFragment;

@FragmentScope
@Component(modules = ScoreGoodsListModule.class, dependencies = AppComponent.class)
public interface ScoreGoodsListComponent {
    void inject(ScoreGoodsListFragment fragment);
}