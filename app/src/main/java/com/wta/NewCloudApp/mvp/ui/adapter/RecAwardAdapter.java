package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.Bill;

import java.util.List;

public class RecAwardAdapter extends BaseQuickAdapter<Bill, BaseViewHolder> {
    public RecAwardAdapter(int layoutResId, @Nullable List<Bill> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Bill item) {
        helper.setText(R.id.tv_title, item.title);
        helper.setText(R.id.tv_time, item.time);
        helper.setText(R.id.tv_money, item.money);
    }
}
