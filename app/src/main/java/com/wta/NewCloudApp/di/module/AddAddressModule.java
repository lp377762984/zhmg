package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.AddAddressContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;


@Module
public class AddAddressModule {
    private AddAddressContract.View view;

    public AddAddressModule(AddAddressContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    AddAddressContract.View provideAddAddressView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideAddAddressModel(UserModel model) {
        return model;
    }
}