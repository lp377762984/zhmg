package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerVIPListComponent;
import com.wta.NewCloudApp.di.module.VIPListModule;
import com.wta.NewCloudApp.mvp.contract.VIPListContract;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.UserClass;
import com.wta.NewCloudApp.mvp.model.entity.VIPInfo;
import com.wta.NewCloudApp.mvp.presenter.VIPListPresenter;
import com.wta.NewCloudApp.mvp.ui.adapter.ClazzAdapter;

import java.util.List;

/**
 * 会员级别列表
 */
public class VIPListActivity extends BaseListActivity<VIPListPresenter> implements VIPListContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerVIPListComponent
                .builder()
                .appComponent(appComponent)
                .vIPListModule(new VIPListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_viplist;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    protected void getAdapter() {
        adapter = new ClazzAdapter(R.layout.clazz_item, data);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                UpdateClazzActivity.startUpdate(VIPListActivity.this, ((UserClass) data.get(position)).grade_id);
            }
        });
    }

    @Override
    public void loadData(boolean isRefresh) {
        mPresenter.getVIPList();
    }

    @Override
    public boolean needLoadMore() {
        return false;
    }
}
