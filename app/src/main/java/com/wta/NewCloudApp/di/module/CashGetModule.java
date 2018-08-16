package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.CashGetContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;


@Module
public class CashGetModule {
    private CashGetContract.View view;

    public CashGetModule(CashGetContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CashGetContract.View provideCashGetView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideCashGetModel(UserModel model) {
        return model;
    }
}