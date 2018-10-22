package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerExchangeRecordComponent;
import com.wta.NewCloudApp.di.module.ExchangeRecordModule;
import com.wta.NewCloudApp.mvp.contract.ExchangeRecordContract;
import com.wta.NewCloudApp.mvp.presenter.ExchangeRecordPresenter;
import com.wta.NewCloudApp.mvp.ui.fragment.ExchangeRecordFFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 兑换记录
 */
public class ExchangeRecordActivity extends BaseLoadingActivity<ExchangeRecordPresenter> implements ExchangeRecordContract.View {

    @BindView(R.id.tabLayout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private String titles[] = new String[]{"全部", "待发货", "已完成"};

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerExchangeRecordComponent
                .builder()
                .appComponent(appComponent)
                .exchangeRecordModule(new ExchangeRecordModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_exchange_record;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tabLayout.setViewPager(viewPager, titles, this, createFragments());
    }

    private ArrayList<Fragment> createFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        ExchangeRecordFFragment all = ExchangeRecordFFragment.getInstance("all");
        ExchangeRecordFFragment collect = ExchangeRecordFFragment.getInstance("collect");
        ExchangeRecordFFragment complete = ExchangeRecordFFragment.getInstance("complete");
        fragments.add(all);
        fragments.add(collect);
        fragments.add(complete);
        return fragments;
    }

    @Override
    public boolean useFragment() {
        return true;
    }
}
