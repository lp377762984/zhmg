package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.component.DaggerUScoreDetComponent;
import com.wta.NewCloudApp.di.module.UScoreDetModule;
import com.wta.NewCloudApp.mvp.contract.UScoreDetContract;
import com.wta.NewCloudApp.mvp.presenter.UScoreDetPresenter;

import com.wta.NewCloudApp.R;

/**
 * 用户消费明细
 */
public class UScoreDetActivity extends BaseLoadingActivity<UScoreDetPresenter> implements UScoreDetContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUScoreDetComponent
                .builder()
                .appComponent(appComponent)
                .uScoreDetModule(new UScoreDetModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_uscore_det;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
