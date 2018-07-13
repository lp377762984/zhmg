package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.mvp.IPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wta.NewCloudApp.mvp.ui.widget.CustomLoadMoreView;
import com.wta.NewCloudApp.mvp.view.BaseDataView;

import java.util.ArrayList;
import java.util.List;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class BaseListActivity<P extends IPresenter> extends BaseLoadingActivity<P> implements BaseDataView {
    protected BaseQuickAdapter adapter;
    protected List data = new ArrayList<>();
    protected SmartRefreshLayout refreshLayout;
    protected RecyclerView recyclerView;
    protected boolean isRefresh = true;
    protected boolean isComplete;

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getAdapter();
        if (adapter == null) throw new NullPointerException("adapter is null");
        adapter.setEnableLoadMore(true);
        adapter.setLoadMoreView(new CustomLoadMoreView());
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRefresh = false;
                loadData(isRefresh);
                //mPresenter.getData(data.get(data.size() - 1).getId());
            }
        }, recyclerView);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadmore(false);
        ClassicsHeader ch = new ClassicsHeader(this);
        ch.setTextSizeTitle(COMPLEX_UNIT_SP,14);
        ch.setDrawableArrowSize(15);
        ch.setDrawableProgressSize(15);
        ch.setEnableLastTime(false);
        refreshLayout.setRefreshHeader(ch);
        refreshLayout.setHeaderHeight(48);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isRefresh = true;
                loadData(isRefresh);
                //mPresenter.getData(null);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        loadData(isRefresh);
        //mPresenter.getData(null);
    }

    private void getAdapter() {

    }

    @Override
    public void getData(int what, List msgs) {
        if (data.size() < 6) isComplete = true;
        if (isRefresh) {
            data.clear();
            if (msgs != null) {
                data.addAll(msgs);
                adapter.notifyDataSetChanged();
            }
        } else {
            int beforeSize = data.size();
            if (msgs != null) {
                data.addAll(msgs);
                adapter.notifyItemRangeInserted(beforeSize, msgs.size());
            }
        }
    }
    @Override
    public void showListLoading() {
        if (isRefresh) ;//下拉刷新初次不显示
        else {
            if (isComplete) adapter.loadMoreEnd();
            else adapter.loadMoreComplete();
        }
    }

    @Override
    public void hideListLoading() {
        if (isRefresh) refreshLayout.finishRefresh();
        else {
            if (isComplete) adapter.loadMoreEnd();
            else adapter.loadMoreComplete();
        }
    }

    public void loadData(boolean isRefresh) {

    }
}
