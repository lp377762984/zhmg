package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.Benifit;

import java.util.List;

public class BenifitAdapter extends BaseQuickAdapter<Benifit, BaseViewHolder> {
    public BenifitAdapter(int layoutResId, @Nullable List<Benifit> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Benifit item) {
        helper.setText(R.id.tv_title, item.title);
        helper.setText(R.id.tv_content, item.content);
    }
}
