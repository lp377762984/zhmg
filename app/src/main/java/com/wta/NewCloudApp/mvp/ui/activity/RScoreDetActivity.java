package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.component.DaggerRScoreComponent;
import com.wta.NewCloudApp.di.module.RScoreModule;
import com.wta.NewCloudApp.mvp.contract.RScoreContract;
import com.wta.NewCloudApp.mvp.presenter.RScorePresenter;

import com.wta.NewCloudApp.R;

/**
 * 推荐明细
 */
public class RScoreDetActivity extends BaseLoadingActivity<RScorePresenter> implements RScoreContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRScoreComponent
                .builder()
                .appComponent(appComponent)
                .rScoreModule(new RScoreModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_rscore;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
