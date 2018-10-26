package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.ui.fragment.BSGOrderFFragment;
import com.wta.NewCloudApp.mvp.ui.fragment.ExchangeRecordFFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 礼品订单
 */
public class BSGOrderActivity extends BaseLoadingActivity {
    @BindView(R.id.tabLayout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private String titles[] = new String[]{"全部", "待确认", "已完成"};
    ArrayList<Fragment> fragments;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bsgorder; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        createFragments();
        tabLayout.setViewPager(viewPager, titles, this, fragments);
    }

    private void createFragments() {
        fragments = new ArrayList<>();
        BSGOrderFFragment all = BSGOrderFFragment.newInstance(-1);
        BSGOrderFFragment collect = BSGOrderFFragment.newInstance(0);
        BSGOrderFFragment complete = BSGOrderFFragment.newInstance(1);
        fragments.add(all);
        fragments.add(collect);
        fragments.add(complete);
    }

    @Override
    public boolean useFragment() {
        return true;
    }
}
