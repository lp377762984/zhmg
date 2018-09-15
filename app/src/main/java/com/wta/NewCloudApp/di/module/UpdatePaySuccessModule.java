package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.UpdatePaySuccessContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;


@Module
public class UpdatePaySuccessModule {
    private UpdatePaySuccessContract.View view;

    public UpdatePaySuccessModule(UpdatePaySuccessContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UpdatePaySuccessContract.View provideUpdatePaySuccessView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideUpdatePaySuccessModel(UserModel model) {
        return model;
    }
}