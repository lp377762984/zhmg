package com.wta.NewCloudApp.mvp.ui.adapter;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
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
        GlideArms.with(mContext).load(item.shop_doorhead)
                .placeholder(R.mipmap.side_b_placeholder).into((ImageView) helper.getView(R.id.im_head));
        helper.setText(R.id.tv_name, item.shop_name);
        helper.setText(R.id.tv_type, item.type_name);
        TextView tvClass = helper.getView(R.id.tv_class);
        String level_name = item.level_name;
        SpannableString ss = new SpannableString(level_name + " 级店铺");
        ss.setSpan(new ForegroundColorSpan(0xfff24965), 0, level_name.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(new StyleSpan(Typeface.BOLD), 0, level_name.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvClass.setText(ss);
        if (TextUtils.isEmpty(item.address_details)) {
            helper.setText(R.id.tv_location, item.location_address);
        } else {
            helper.setText(R.id.tv_location, item.location_address + item.address_details);
        }
        helper.setText(R.id.tv_distance, item.distance);
        TextView tvType = helper.getView(R.id.tv_type);
        GlideArms.with(mContext).load(item.type_img).error(R.mipmap.side_type).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                resource.setBounds(0, 0, resource.getMinimumWidth(), resource.getMinimumHeight());
                tvType.setCompoundDrawables(resource, null, null, null);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                errorDrawable.setBounds(0, 0, errorDrawable.getMinimumWidth(), errorDrawable.getMinimumHeight());
                tvType.setCompoundDrawables(errorDrawable, null, null, null);
            }
        });
        helper.setVisible(R.id.tv_gift, item.is_gift==1);
    }
}
