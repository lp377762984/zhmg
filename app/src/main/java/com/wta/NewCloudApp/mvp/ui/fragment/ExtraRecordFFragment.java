package com.wta.NewCloudApp.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerExtraRecordFComponent;
import com.wta.NewCloudApp.di.module.ExtraRecordFModule;
import com.wta.NewCloudApp.mvp.contract.ExtraRecordFContract;
import com.wta.NewCloudApp.mvp.model.entity.Goods2;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.presenter.ExtraRecordFPresenter;
import com.wta.NewCloudApp.mvp.ui.adapter.ExtraRecordAdapter;


public class ExtraRecordFFragment extends BaseListFragment<ExtraRecordFPresenter> implements ExtraRecordFContract.View {
    private int status = -3;
    private boolean isViewCreated;
    private boolean hasLoadData;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerExtraRecordFComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .extraRecordFModule(new ExtraRecordFModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_extra_record, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        status = getArguments().getInt("status");
        super.initData(savedInstanceState);
        if (!hasLoadData && getUserVisibleHint()) {
            loadData(isRefresh);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
    }

    @Override
    public void loadData(boolean isRefresh) {
        hasLoadData = true;
        mPresenter.getExtraRecordList(status, isRefresh);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isViewCreated) {
            isRefresh = true;
            loadData(isRefresh);
        }

    }

    public static ExtraRecordFFragment getInstance(int status) {
        ExtraRecordFFragment fragment = new ExtraRecordFFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void getAdapter() {
        adapter = new ExtraRecordAdapter(R.layout.extra_record_item, data);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Goods2 goods = (Goods2) data.get(position);
                switch (view.getId()) {
                    case R.id.btn_more://确认收货
                        mPresenter.sureGetGoods(goods.acid);
                        break;
                }
            }
        });
    }
    @Override
    public void confirmSuccess(Result result) {
        showToast("确认收货成功");
        isRefresh = true;
        loadData(isRefresh);
    }

    @Override
    protected boolean autoRequest() {
        return false;
    }

}
