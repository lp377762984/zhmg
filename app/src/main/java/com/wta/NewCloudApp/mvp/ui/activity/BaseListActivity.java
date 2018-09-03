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
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.ui.widget.CustomLoadMoreView;
import com.wta.NewCloudApp.mvp.view.BaseDataView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class BaseListActivity<P extends IPresenter> extends BaseLoadingActivity<P> implements BaseDataView {
    protected BaseQuickAdapter adapter;
    protected List data = new ArrayList<>();
    @BindView(R.id.refresh_layout)
    protected SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;
    protected boolean isRefresh = true;
    protected boolean isComplete;

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getAdapter();
        if (adapter == null) throw new NullPointerException("adapter is null");
        if (needLoadmore()) {
            adapter.setEnableLoadMore(true);
            adapter.setLoadMoreView(new CustomLoadMoreView());
            adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    isRefresh = false;
                    loadData(isRefresh);
                }
            }, recyclerView);
        }
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadmore(false);
        ClassicsHeader ch = new ClassicsHeader(this);
        ch.setTextSizeTitle(COMPLEX_UNIT_SP, 14);
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
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        try {
            adapter.setEmptyView(R.layout.empty_side);
        } catch (Exception e) {
            adapter.bindToRecyclerView(recyclerView);
        }
        if (autoRequest())
            loadData(isRefresh);
    }

    protected void getAdapter() {

    }

    @Override
    public void getData(int what, Result<List> result) {
        List msgs = result.data;
        if (!isRefresh && (msgs == null || msgs.size() == 0))
            isComplete = true;
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
        if (isRefresh) showLoading();
    }

    @Override
    public void hideListLoading() {
        if (isRefresh) {
            hideLoading();
            refreshLayout.finishRefresh();
        } else {
            if (isComplete) adapter.loadMoreEnd();
            else adapter.loadMoreComplete();
        }
    }

    @Override
    public void loadFailed() {
        adapter.loadMoreFail();
    }

    public void loadData(boolean isRefresh) {

    }

    public boolean needLoadmore() {
        return true;
    }

    public boolean autoRequest() {
        return true;
    }
}
