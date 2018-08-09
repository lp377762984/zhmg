package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.BClass;

import java.util.List;

public class ClassAdapter extends BaseQuickAdapter<BClass, BaseViewHolder> {
    public ClassAdapter(int layoutResId, @Nullable List<BClass> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BClass item) {
        helper.setText(R.id.tv_title, item.level_name);
        helper.setText(R.id.tv_content, item.level_content);
    }
}
