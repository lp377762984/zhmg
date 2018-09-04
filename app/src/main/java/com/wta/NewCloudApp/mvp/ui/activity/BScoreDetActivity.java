package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.component.DaggerBScoreComponent;
import com.wta.NewCloudApp.di.module.BScoreModule;
import com.wta.NewCloudApp.mvp.contract.BScoreContract;
import com.wta.NewCloudApp.mvp.presenter.BScorePresenter;

import com.wta.NewCloudApp.R;

/**
 * 商户收款明细
 */
public class BScoreDetActivity extends BaseLoadingActivity<BScorePresenter> implements BScoreContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBScoreComponent
                .builder()
                .appComponent(appComponent)
                .bScoreModule(new BScoreModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bscore;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
