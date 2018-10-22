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
import com.wta.NewCloudApp.di.component.DaggerExchangeRecordFComponent;
import com.wta.NewCloudApp.di.module.ExchangeRecordFModule;
import com.wta.NewCloudApp.mvp.contract.ExchangeRecordFContract;
import com.wta.NewCloudApp.mvp.model.entity.ScoreGoods;
import com.wta.NewCloudApp.mvp.presenter.ExchangeRecordFPresenter;
import com.wta.NewCloudApp.mvp.ui.activity.SideDetActivity;
import com.wta.NewCloudApp.mvp.ui.adapter.ExchangeRecordAdapter;


public class ExchangeRecordFFragment extends BaseListFragment<ExchangeRecordFPresenter> implements ExchangeRecordFContract.View {


    private String status;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerExchangeRecordFComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .exchangeRecordFModule(new ExchangeRecordFModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exchange_record, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        status = getArguments().getString("status");
        super.initData(savedInstanceState);
    }

    public static ExchangeRecordFFragment getInstance(String status) {
        ExchangeRecordFFragment fragment = new ExchangeRecordFFragment();
        Bundle bundle = new Bundle();
        bundle.putString("status", status);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void getAdapter() {
        adapter = new ExchangeRecordAdapter(R.layout.exchange_record_item, data);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ScoreGoods scoreGoods = (ScoreGoods) data.get(position);
                switch (view.getId()) {
                    case R.id.btn_more://再次兑换

                        break;
                    case R.id.tv_store_name:
                        SideDetActivity.startDet(getActivity(), scoreGoods.store_id, 1);
                        break;
                }
            }
        });
    }

    @Override
    public void loadData(boolean isRefresh) {
        mPresenter.getExchangeRec(status, isRefresh);
    }
}
