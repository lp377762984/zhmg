package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.component.DaggerPayOverComponent;
import com.wta.NewCloudApp.di.module.PayOverModule;
import com.wta.NewCloudApp.mvp.contract.PayOverContract;
import com.wta.NewCloudApp.mvp.presenter.PayOverPresenter;

import com.wta.NewCloudApp.R;


public class PayOverActivity extends BaseLoadingActivity<PayOverPresenter> implements PayOverContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPayOverComponent
                .builder()
                .appComponent(appComponent)
                .payOverModule(new PayOverModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_pay_over;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
