package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wta.NewCloudApp.jiuwei210278.R;
import com.wta.NewCloudApp.mvp.model.entity.Business;

import java.util.List;

public class SideAdapter extends BaseQuickAdapter<Business, BaseViewHolder> {
    public SideAdapter(int layoutResId, @Nullable List<Business> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Business item) {
        Glide.with(mContext).load(item.icon).into((ImageView) helper.getView(R.id.im_icon));
        helper.setText(R.id.tv_name, item.name);
        helper.setText(R.id.tv_type, item.type);
        helper.setText(R.id.tv_score_type, item.scoreType);
        helper.setText(R.id.tv_time, item.time);
        helper.setText(R.id.tv_location, item.location);
        helper.setText(R.id.tv_distance, item.distance);
    }
}
