package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerSideSearchComponent;
import com.wta.NewCloudApp.di.module.SideSearchModule;
import com.wta.NewCloudApp.mvp.contract.SideSearchContract;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.presenter.SideSearchPresenter;
import com.wta.NewCloudApp.mvp.ui.adapter.SideAdapter;
import com.wta.NewCloudApp.mvp.ui.widget.ClearEditText;

import butterknife.BindView;
import butterknife.OnClick;


public class SideSearchActivity extends BaseListActivity<SideSearchPresenter> implements SideSearchContract.View {

    @BindView(R.id.et_content)
    ClearEditText etContent;
    private String keywords;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSideSearchComponent
                .builder()
                .appComponent(appComponent)
                .sideSearchModule(new SideSearchModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_side_search;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        adapter.setEmptyView(R.layout.empty_side);
    }

    @OnClick(R.id.tv_go_in)
    public void clickView() {
        keywords = etContent.getText().toString();
        if (TextUtils.isEmpty(keywords)) {
            showToast("请输入关键词");
            return;
        }
        isRefresh = true;
        mPresenter.getSearchResult(isRefresh, keywords);
    }

    @Override
    protected void getAdapter() {
        adapter = new SideAdapter(R.layout.side_item, data);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Business business = (Business) data.get(position);
                SideDetActivity.startDet(SideSearchActivity.this, business.store_id, business.is_gift);
            }
        });
    }

    @Override
    public void loadData(boolean isRefresh) {
        if (TextUtils.isEmpty(keywords)) {
            showToast("请输入关键词");
            return;
        }
        mPresenter.getSearchResult(isRefresh, keywords);
    }

    @Override
    public boolean autoRequest() {
        return false;
    }
}
