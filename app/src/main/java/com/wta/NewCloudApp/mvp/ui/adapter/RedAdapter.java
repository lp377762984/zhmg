package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.Red;

import java.util.List;

public class RedAdapter extends BaseQuickAdapter<Red, BaseViewHolder> {
    public RedAdapter(int layoutResId, @Nullable List<Red> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Red item) {
        helper.setText(R.id.tv_title, item.title);
        helper.setText(R.id.tv_time, item.time);
        helper.setText(R.id.tv_money, item.money);
    }
}
