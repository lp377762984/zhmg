package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.component.DaggerAboutUsComponent;
import com.wta.NewCloudApp.di.module.AboutUsModule;
import com.wta.NewCloudApp.mvp.contract.AboutUsContract;
import com.wta.NewCloudApp.mvp.presenter.AboutUsPresenter;

import com.wta.NewCloudApp.R;


public class AboutUsActivity extends BaseLoadingActivity<AboutUsPresenter> implements AboutUsContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAboutUsComponent
                .builder()
                .appComponent(appComponent)
                .aboutUsModule(new AboutUsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_about_us;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
