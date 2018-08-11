package com.wta.NewCloudApp.mvp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerSideComponent;
import com.wta.NewCloudApp.di.module.SideModule;
import com.wta.NewCloudApp.mvp.contract.SideContract;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.presenter.SidePresenter;
import com.wta.NewCloudApp.mvp.ui.activity.SideDetActivity;
import com.wta.NewCloudApp.mvp.ui.adapter.SideAdapter;


public class SideFragment extends BaseListFragment<SidePresenter> implements SideContract.View {
    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSideComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .sideModule(new SideModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_side, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    protected void getAdapter() {
        adapter = new SideAdapter(R.layout.side_item, data);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SideDetActivity.startDet(getActivity(), ((Business) data.get(position)).store_id);
            }
        });
    }

    @Override
    public void loadData(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }

    @Override
    public Activity getFragmentContext() {
        return getActivity();
    }

    @Override
    public void complete() {
        isComplete = true;
    }
}
