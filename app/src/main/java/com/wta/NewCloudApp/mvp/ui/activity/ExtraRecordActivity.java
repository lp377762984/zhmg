package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.ui.fragment.ExtraRecordFFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class ExtraRecordActivity extends BaseLoadingActivity {
    @BindView(R.id.tabLayout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private String titles[] = new String[]{"全部", "待发货", "待收货", "已完成"};
    ArrayList<Fragment> fragments;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_extra_record;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        createFragments();
        tabLayout.setViewPager(viewPager, titles, this, fragments);
    }

    private void createFragments() {
        fragments = new ArrayList<>();
        ExtraRecordFFragment all = ExtraRecordFFragment.getInstance(-1);
        ExtraRecordFFragment collect = ExtraRecordFFragment.getInstance(0);
        ExtraRecordFFragment complete = ExtraRecordFFragment.getInstance(1);
        ExtraRecordFFragment extra = ExtraRecordFFragment.getInstance(2);
        fragments.add(all);
        fragments.add(collect);
        fragments.add(complete);
        fragments.add(extra);
    }

    @Override
    public boolean useFragment() {
        return true;
    }
}
