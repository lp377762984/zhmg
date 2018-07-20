package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.CardListContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.UserModel;

import dagger.Module;
import dagger.Provides;


@Module
public class CardListModule {
    private CardListContract.View view;

    public CardListModule(CardListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CardListContract.View provideCardListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IUserModel provideCardListModel(UserModel model) {
        return model;
    }
}