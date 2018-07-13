package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.UserMsgContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;


@Module
public class UserMsgModule {
    private UserMsgContract.View view;

    /**
     * 构建UserMsgModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public UserMsgModule(UserMsgContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UserMsgContract.View provideUserMsgView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideUserMsgModel(UserModel model) {
        return model;
    }
}