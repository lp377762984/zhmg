package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.Benifit;
import com.wta.NewCloudApp.mvp.model.entity.Power;

import java.util.List;

public class BenifitAdapter extends BaseQuickAdapter<Power, BaseViewHolder> {
    public BenifitAdapter(int layoutResId, @Nullable List<Power> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Power item) {
        helper.setText(R.id.tv_name, item.server_title);
        helper.setText(R.id.tv_content, item.server_desc);
    }
}
