package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.Score;

import java.util.List;

public class GroupGetAdapter extends BaseQuickAdapter<Score, BaseViewHolder> {
    public GroupGetAdapter(int layoutResId, @Nullable List<Score> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Score item) {
        helper.setText(R.id.tv_name, item.name);
        helper.setText(R.id.tv_score, item.score);
        helper.setText(R.id.tv_time, item.time);
    }
}
