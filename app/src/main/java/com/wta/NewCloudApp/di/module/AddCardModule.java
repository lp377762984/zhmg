package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.AddCardContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;


@Module
public class AddCardModule {
    private AddCardContract.View view;

    public AddCardModule(AddCardContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    AddCardContract.View provideAddCardView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideAddCardModel(UserModel model) {
        return model;
    }
}