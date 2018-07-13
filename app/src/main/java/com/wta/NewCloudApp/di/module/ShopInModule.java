package com.wta.NewCloudApp.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.wta.NewCloudApp.mvp.contract.ShopInContract;
import com.wta.NewCloudApp.mvp.model.BusinessModel;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;


@Module
public class ShopInModule {
    private ShopInContract.View view;

    /**
     * 构建ShopInModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ShopInModule(ShopInContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ShopInContract.View provideShopInView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    IBusinessModel provideShopInModel(BusinessModel model) {
        return model;
    }
}