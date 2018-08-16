package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.component.DaggerBGroupListComponent;
import com.wta.NewCloudApp.di.module.BGroupListModule;
import com.wta.NewCloudApp.mvp.contract.BGroupListContract;
import com.wta.NewCloudApp.mvp.model.entity.Score;
import com.wta.NewCloudApp.mvp.presenter.BGroupListPresenter;

import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.ui.adapter.GroupGetAdapter;

/**
 * 团队收益明细
 */
public class BGroupListActivity extends BaseListActivity<BGroupListPresenter> implements BGroupListContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBGroupListComponent
                .builder()
                .appComponent(appComponent)
                .bGroupListModule(new BGroupListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bgroup_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        for (int i = 0; i < 10; i++) {
            data.add(new Score("山间一壶酒","18.5.10","5600"));
        }
    }

    @Override
    protected void getAdapter() {
        adapter = new GroupGetAdapter(R.layout.group_get_item,data);
    }

    @Override
    public void loadData(boolean isRefresh) {

    }
}
