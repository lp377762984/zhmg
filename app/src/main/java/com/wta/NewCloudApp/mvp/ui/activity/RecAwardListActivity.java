package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerRecAwardListComponent;
import com.wta.NewCloudApp.di.module.RecAwardListModule;
import com.wta.NewCloudApp.mvp.contract.RecAwardListContract;
import com.wta.NewCloudApp.mvp.presenter.RecAwardListPresenter;
import com.wta.NewCloudApp.mvp.ui.adapter.RecAwardAdapter;

/**
 * 推荐获得奖励明细
 */
public class RecAwardListActivity extends BaseListActivity<RecAwardListPresenter> implements RecAwardListContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRecAwardListComponent
                .builder()
                .appComponent(appComponent)
                .recAwardListModule(new RecAwardListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_rec_award_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    protected void getAdapter() {
        adapter =new RecAwardAdapter(R.layout.rec_award_item,data);
    }

    @Override
    public void loadData(boolean isRefresh) {
        mPresenter.getAwardBill(isRefresh);
    }
}
