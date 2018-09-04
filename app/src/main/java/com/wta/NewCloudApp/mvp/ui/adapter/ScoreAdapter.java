package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.model.entity.Score;

import java.util.List;

public class ScoreAdapter extends BaseQuickAdapter<Bill, BaseViewHolder> {
    public ScoreAdapter(int layoutResId, @Nullable List<Bill> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Bill item) {
        helper.setText(R.id.tv_name, item.type);
        helper.setText(R.id.tv_time, item.time);
        helper.setText(R.id.tv_score, item.score);
    }
}
