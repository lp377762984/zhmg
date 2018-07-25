package com.wta.NewCloudApp.di.module;


import com.wta.NewCloudApp.mvp.contract.AddRecContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;


@Module
public class AddRecModule {
    private AddRecContract.View view;

    public AddRecModule(AddRecContract.View view) {
        this.view = view;
    }

    @Provides
    AddRecContract.View provideAddRecView() {
        return this.view;
    }

    @Provides
    IUserModel provideAddRecModel(UserModel model) {
        return model;
    }
}