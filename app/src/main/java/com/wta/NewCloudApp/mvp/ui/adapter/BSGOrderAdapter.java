package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.SGDet;

import java.util.List;

public class BSGOrderAdapter extends BaseQuickAdapter<SGDet, BaseViewHolder> {

    public BSGOrderAdapter(int layoutResId, @Nullable List<SGDet> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SGDet item) {
        GlideArms.with(mContext).load(item.goods_img).placeholder(R.mipmap.side_b_placeholder)
                .into((ImageView) helper.getView(R.id.im_goods));
        //set unit score
        String s11 = item.integral;
        String s22 = " 积分";
        SpannableString ss = new SpannableString(s11 + s22);
        ss.setSpan(new RelativeSizeSpan(0.75f), s11.length(), ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(0xff333333), s11.length(), ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ((TextView) helper.getView(R.id.tv_score)).setText(ss);
        helper.setText(R.id.tv_name, item.goods_name);
        //set total
        int num = item.num;
        String s1 = "共计" + num + "件商品 合计:";
        String s2 = item.integral;
        String s3 = "积分";
        SpannableString ss1 = new SpannableString(s1 + s2 + s3);
        ss1.setSpan(new ForegroundColorSpan(0xffff4c6a), s1.length(), (s1 + s2).length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        ss1.setSpan(new RelativeSizeSpan(1.27f), s1.length(), (s1 + s2).length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        ss1.setSpan(new ForegroundColorSpan(0xffff4c6a), (s1 + s2).length(), ss1.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        ((TextView) helper.getView(R.id.tv_total)).setText(ss1);
        helper.setText(R.id.tv_order_no, item.ordersn);
        helper.setText(R.id.tv_create_time, item.create_time);
        helper.setText(R.id.tv_confirm_time, item.update_time);
        helper.setVisible(R.id.btn_line, item.status == 0);
        helper.setGone(R.id.btn_btm, item.status == 0);
        helper.addOnClickListener(R.id.btn_btm);
    }
}
