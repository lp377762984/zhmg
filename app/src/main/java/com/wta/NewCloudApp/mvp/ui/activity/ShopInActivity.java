package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.di.component.DaggerShopInComponent;
import com.wta.NewCloudApp.di.module.ShopInModule;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.contract.ShopInContract;
import com.wta.NewCloudApp.mvp.presenter.ShopInPresenter;

/**
 * 店铺入驻
 */
public class ShopInActivity extends BaseLoadingActivity<ShopInPresenter> implements ShopInContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerShopInComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shopInModule(new ShopInModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_shop_in; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
