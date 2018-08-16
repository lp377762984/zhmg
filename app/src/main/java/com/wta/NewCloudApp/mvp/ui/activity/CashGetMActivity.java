package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.component.DaggerCashGetComponent;
import com.wta.NewCloudApp.di.module.CashGetModule;
import com.wta.NewCloudApp.mvp.contract.CashGetContract;
import com.wta.NewCloudApp.mvp.model.entity.Score;
import com.wta.NewCloudApp.mvp.presenter.CashGetPresenter;

import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.ui.adapter.CashGetAdapter;


public class CashGetMActivity extends BaseListActivity<CashGetPresenter> implements CashGetContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCashGetComponent
                .builder()
                .appComponent(appComponent)
                .cashGetModule(new CashGetModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_cash_get;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        for (int i = 0; i < 5; i++) {
            data.add(new Score("","墙头草","08.25 21:35","2500"));
        }
    }

    @Override
    protected void getAdapter() {
        adapter=new CashGetAdapter(R.layout.score_item,data);
    }
}
