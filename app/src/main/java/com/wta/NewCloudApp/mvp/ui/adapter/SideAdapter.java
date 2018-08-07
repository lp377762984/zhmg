package com.wta.NewCloudApp.mvp.ui.adapter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.Business;

import java.util.List;

public class SideAdapter extends BaseQuickAdapter<Business, BaseViewHolder> {
    public SideAdapter(int layoutResId, @Nullable List<Business> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Business item) {
        GlideArms.with(mContext).load(item.shop_doorhead).placeholder(new ColorDrawable(Color.GRAY)).into((ImageView) helper.getView(R.id.im_icon));
        helper.setText(R.id.tv_name, item.shop_name);
        helper.setText(R.id.tv_type, item.type_name);
        helper.setText(R.id.tv_score_type, item.level_name);
        helper.setText(R.id.tv_location, item.location_address);
        helper.setText(R.id.tv_distance, item.distance);
    }
}
