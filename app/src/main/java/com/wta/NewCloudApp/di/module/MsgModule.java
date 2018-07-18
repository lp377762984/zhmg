package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.MsgContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;


@Module
public class MsgModule {
    private MsgContract.View view;

    public MsgModule(MsgContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MsgContract.View provideMsgView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideMsgModel(UserModel model) {
        return model;
    }
}