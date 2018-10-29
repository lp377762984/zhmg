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

/**
 * 兑换记录
 */
public class ExchangeRecordActivity extends BaseLoadingActivity<ExchangeRecordPresenter> implements ExchangeRecordContract.View {

    @BindView(R.id.tabLayout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private String titles[] = new String[]{"全部", "待收货", "已完成"};
    ArrayList<Fragment> fragments;

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
        createFragments();
        tabLayout.setViewPager(viewPager, titles, this, fragments);
    }

    private void createFragments() {
        fragments = new ArrayList<>();
        ExchangeRecordFFragment all = ExchangeRecordFFragment.getInstance(-1);
        ExchangeRecordFFragment collect = ExchangeRecordFFragment.getInstance(0);
        ExchangeRecordFFragment complete = ExchangeRecordFFragment.getInstance(1);
        fragments.add(all);
        fragments.add(collect);
        fragments.add(complete);
    }

    @Override
    public boolean useFragment() {
        return true;
    }
}
