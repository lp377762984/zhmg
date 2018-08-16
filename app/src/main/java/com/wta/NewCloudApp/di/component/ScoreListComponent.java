package com.wta.NewCloudApp.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.module.ScoreListModule;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.ui.activity.ScoreListActivity;
import com.wta.NewCloudApp.mvp.ui.fragment.ScoreListFragment;

@ActivityScope
@Component(modules = ScoreListModule.class, dependencies = AppComponent.class)
public interface ScoreListComponent {
    void inject(ScoreListActivity activity);

    void inject(ScoreListFragment fragment);
}