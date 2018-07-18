package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.AuthContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;


@Module
public class AuthModule {
    private AuthContract.View view;

    /**
     * 构建AuthModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public AuthModule(AuthContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    AuthContract.View provideAuthView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideAuthModel(UserModel model) {
        return model;
    }
}