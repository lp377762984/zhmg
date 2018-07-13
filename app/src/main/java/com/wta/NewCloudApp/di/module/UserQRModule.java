package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.UserQRContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;


@Module
public class UserQRModule {
    private UserQRContract.View view;

    /**
     * 构建UserQRModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public UserQRModule(UserQRContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UserQRContract.View provideUserQRView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideUserQRModel(UserModel model) {
        return model;
    }
}