package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerScoreListComponent;
import com.wta.NewCloudApp.di.module.ScoreListModule;
import com.wta.NewCloudApp.mvp.contract.ScoreListContract;
import com.wta.NewCloudApp.mvp.model.entity.DetTabEntity;
import com.wta.NewCloudApp.mvp.presenter.ScoreListPresenter;
import com.wta.NewCloudApp.mvp.ui.fragment.ScoreListFragment;
import com.wta.NewCloudApp.mvp.ui.widget.MoneyBar;
import com.wta.NewCloudApp.mvp.ui.widget.tabLayout.CommonTabLayout2;

import java.util.ArrayList;

import butterknife.BindView;


public class ScoreListActivity extends BaseLoadingActivity<ScoreListPresenter> implements ScoreListContract.View {
    @BindView(R.id.tabLayout)
    CommonTabLayout2 tabLayout;

    @BindView(R.id.mb)
    MoneyBar moneyBar;
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
        tabLayout.setTabData(createTabData(), this, R.id.frameLayout, createFragments());
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        moneyBar.setCallBack(moneyBar.new CallbackImp(){
            @Override
            public void clickTail() {
                showToast("暂未开放");
            }
        });
    }

    private ArrayList<Fragment> createFragments() {
        ArrayList<Fragment> fragments=new ArrayList<>(4);
        fragments.add(ScoreListFragment.getInstance(0));
        fragments.add(ScoreListFragment.getInstance(1));
        fragments.add(ScoreListFragment.getInstance(2));
        fragments.add(ScoreListFragment.getInstance(3));
        return fragments;
    }

    private ArrayList<CustomTabEntity> createTabData() {
        ArrayList<CustomTabEntity> customTabEntities = new ArrayList<>(4);
        customTabEntities.add(new DetTabEntity() {
            @Override
            public String getTabTitle() {
                return "全部";
            }
        });
        customTabEntities.add(new DetTabEntity() {
            @Override
            public String getTabTitle() {
                return "销售收益";
            }
        });
        customTabEntities.add(new DetTabEntity() {
            @Override
            public String getTabTitle() {
                return "消费收益";
            }
        });
        customTabEntities.add(new DetTabEntity() {
            @Override
            public String getTabTitle() {
                return "团队收益";
            }
        });
        return customTabEntities;
    }

    @Override
    public boolean useFragment() {
        return true;
    }
}
