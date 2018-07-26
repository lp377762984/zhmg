package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.AddressListContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;


@Module
public class AddressListModule {
    private AddressListContract.View view;

    public AddressListModule(AddressListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    AddressListContract.View provideAddressListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideAddressListModel(UserModel model) {
        return model;
    }
}