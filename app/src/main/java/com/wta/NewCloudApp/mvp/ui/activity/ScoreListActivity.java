package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerScoreListComponent;
import com.wta.NewCloudApp.di.module.ScoreListModule;
import com.wta.NewCloudApp.mvp.contract.ScoreListContract;
import com.wta.NewCloudApp.mvp.model.entity.BillType;
import com.wta.NewCloudApp.mvp.presenter.ScoreListPresenter;
import com.wta.NewCloudApp.mvp.ui.fragment.ScoreListFragment;
import com.wta.NewCloudApp.mvp.ui.widget.MoneyBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class ScoreListActivity extends BaseLoadingActivity<ScoreListPresenter> implements ScoreListContract.View {
    @BindView(R.id.tabLayout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.mb)
    MoneyBar moneyBar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerScoreListComponent
                .builder()
                .appComponent(appComponent)
                .scoreListModule(new ScoreListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_score_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        moneyBar.setCallBack(moneyBar.new CallbackImp() {
            @Override
            public void clickTail() {
                showToast("暂未开放");
            }
        });
        mPresenter.getBillsType();
    }

    private ArrayList<Fragment> createFragments(List<BillType> billType) {
        ArrayList<Fragment> fragments = new ArrayList<>(billType.size());
        for (int i = 0; i < billType.size(); i++) {
            fragments.add(ScoreListFragment.getInstance(billType.get(i)));
        }
        return fragments;
    }

    @Override
    public void showBillsType(List<BillType> billType) {
        tabLayout.setViewPager(viewPager, createTitles(billType), this, createFragments(billType));
    }

    private String[] createTitles(List<BillType> billType) {
        String[] titles = new String[billType.size()];
        for (int i = 0; i < billType.size(); i++) {
            titles[i] = billType.get(i).title;
        }
        return titles;
    }

    @Override
    public Activity getMContext() {
        return this;
    }

    @Override
    public boolean useFragment() {
        return true;
    }

}
