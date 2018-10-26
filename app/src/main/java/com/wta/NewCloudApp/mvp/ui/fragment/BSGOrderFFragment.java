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
import com.wta.NewCloudApp.di.component.DaggerBSGOrderFComponent;
import com.wta.NewCloudApp.di.module.BSGOrderFModule;
import com.wta.NewCloudApp.mvp.contract.BSGOrderFContract;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.SGDet;
import com.wta.NewCloudApp.mvp.presenter.BSGOrderFPresenter;
import com.wta.NewCloudApp.mvp.ui.adapter.BSGOrderAdapter;

/**
 * 礼品订单
 */
public class BSGOrderFFragment extends BaseListFragment<BSGOrderFPresenter> implements BSGOrderFContract.View {

    private int status;

    public static BSGOrderFFragment newInstance(int status) {
        BSGOrderFFragment fragment = new BSGOrderFFragment();
        Bundle b = new Bundle();
        b.putInt("status", status);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerBSGOrderFComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .bSGOrderFModule(new BSGOrderFModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bsgorder, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        status = getArguments().getInt("status");
        super.initData(savedInstanceState);
    }

    @Override
    protected void getAdapter() {
        adapter = new BSGOrderAdapter(R.layout.bsg_order_item, data);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                SGDet sgDet = (SGDet) data.get(position);
                mPresenter.confirmGetGoods(sgDet.order_id);
            }
        });
    }

    @Override
    public void loadData(boolean isRefresh) {
        mPresenter.getBSGOrder(isRefresh,status);
    }

    @Override
    public void confirmSuccess(Result result) {
        showToast("确认取货成功！");
        loadData(true);
    }
}
