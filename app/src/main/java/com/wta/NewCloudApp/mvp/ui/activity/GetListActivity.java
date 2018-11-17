package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerGetListComponent;
import com.wta.NewCloudApp.di.module.GetListModule;
import com.wta.NewCloudApp.mvp.contract.GetListContract;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.presenter.GetListPresenter;
import com.wta.NewCloudApp.mvp.ui.adapter.RedAdapter;

import java.util.List;

/**
 * 红包明细
 */
public class GetListActivity extends BaseListActivity<GetListPresenter> implements GetListContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGetListComponent
                .builder()
                .appComponent(appComponent)
                .getListModule(new GetListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_get_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void getAdapter() {
        adapter = new RedAdapter(R.layout.red_item, data);
    }

    @Override
    public void loadData(boolean isRefresh) {
        mPresenter.getRedList(isRefresh);
    }
}
