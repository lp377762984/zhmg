package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.Bill;

import java.util.List;


public class HomeListAdapter extends BaseQuickAdapter<Bill, BaseViewHolder> {
    public HomeListAdapter(@LayoutRes int layoutResId, @Nullable List<Bill> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Bill item) {
        helper.addOnClickListener(R.id.tv_more);
        helper.setText(R.id.tv_title, item.maxTitle);
        helper.setText(R.id.tv_time, item.createTime);
        helper.setText(R.id.tv_content, item.score);
        helper.setText(R.id.tv_type, item.minTtile);
        GlideArms.with(mContext).load(item.avatar).placeholder(R.mipmap.home_circle_placeholder)
                .into((ImageView) helper.getView(R.id.im_icon));
    }
}
