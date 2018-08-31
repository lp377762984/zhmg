package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.component.DaggerBRecordComponent;
import com.wta.NewCloudApp.di.module.BRecordModule;
import com.wta.NewCloudApp.mvp.contract.BRecordContract;
import com.wta.NewCloudApp.mvp.presenter.BRecordPresenter;

import com.wta.NewCloudApp.R;


public class BRecordActivity extends BaseLoadingActivity<BRecordPresenter> implements BRecordContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBRecordComponent
                .builder()
                .appComponent(appComponent)
                .bRecordModule(new BRecordModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_brecord;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
