package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

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

    private TextView tvTotal;

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
        super.initData(savedInstanceState);
    }

    @Override
    protected void getAdapter() {
        adapter = new RedAdapter(R.layout.red_item, data);
        View headView = getLayoutInflater().inflate(R.layout.red_head, null);
        tvTotal = headView.findViewById(R.id.tv_total);
        adapter.addHeaderView(headView);
    }

    @Override
    public void loadData(boolean isRefresh) {
        mPresenter.getRedList(isRefresh);
    }

    @Override
    public void getData(int what, Result<List> result) {
        try {
            tvTotal.setText(result.p_money);
        } catch (Exception e) {
            tvTotal.setText("0");
        }
        super.getData(what, result);
    }
}
