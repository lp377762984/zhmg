package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.CashDetContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;


@Module
public class CashDetModule {
    private CashDetContract.View view;

    public CashDetModule(CashDetContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CashDetContract.View provideCashDetView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideCashDetModel(UserModel model) {
        return model;
    }
}