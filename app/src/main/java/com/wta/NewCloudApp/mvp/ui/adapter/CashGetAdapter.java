package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.Score;

import java.util.List;

public class CashGetAdapter extends BaseQuickAdapter<Score,BaseViewHolder> {
    public CashGetAdapter(int layoutResId, @Nullable List<Score> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Score item) {
        helper.setText(R.id.tv_name, item.name);
        helper.setText(R.id.tv_time, item.time);
        helper.setText(R.id.tv_score, item.score);
        GlideArms.with(mContext).load(item.icon)
                .placeholder(R.mipmap.user_default).into((ImageView) helper.getView(R.id.im_head));
    }
}
