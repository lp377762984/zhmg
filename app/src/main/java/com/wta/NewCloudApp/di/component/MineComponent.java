package com.wta.NewCloudApp.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.di.module.MineModule;
import com.wta.NewCloudApp.mvp.ui.fragment.MineFragment;

import dagger.Component;

@ActivityScope
@Component(modules = MineModule.class, dependencies = AppComponent.class)
public interface MineComponent {
    void inject(MineFragment fragment);
}