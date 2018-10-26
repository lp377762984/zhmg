package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.ScoreGoods;

import java.util.List;

public class ExchangeRecordAdapter extends BaseQuickAdapter<ScoreGoods, BaseViewHolder> {
    public ExchangeRecordAdapter(int layoutResId, @Nullable List<ScoreGoods> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScoreGoods item) {
        GlideArms.with(mContext).load(item.sign_img).into((ImageView) helper.getView(R.id.im_store_logo));
        helper.setText(R.id.tv_store_name, !TextUtils.isEmpty(item.store_name) ? item.store_name : item.title);
        GlideArms.with(mContext).load(item.goods_img).placeholder(R.mipmap.side_b_placeholder).into((ImageView) helper.getView(R.id.im_goods));
        helper.setText(R.id.tv_name, item.goods_name);
        //set unit score
        String score = item.unit_integral;
        String scoTrs = " 积分";
        SpannableString ss = new SpannableString(score + scoTrs);
        ss.setSpan(new RelativeSizeSpan(0.7f), score.length(), ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(0xff333333), score.length(), ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        TextView tvScore = helper.getView(R.id.tv_score);
        tvScore.setText(ss);
        //set total score
        String before = "合计:";
        String totalSco = item.integral;
        String after = " 积分";

        SpannableString ss1 = new SpannableString(before + totalSco + after);
        ss1.setSpan(new RelativeSizeSpan(1.3f), before.length(), (before + totalSco).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss1.setSpan(new ForegroundColorSpan(0xffff4c6a), before.length(), (before + totalSco).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        ss1.setSpan(new ForegroundColorSpan(0xffff4c6a), (before + totalSco).length(), ss1.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        TextView tvTotalScore = helper.getView(R.id.tv_total_score);
        tvTotalScore.setText(ss1);

        helper.setText(R.id.tv_total_count, "共计" + item.num + "件商品");
        helper.setGone(R.id.btn_more, !(item.type == 2 && item.status == 0));
        helper.setText(R.id.btn_more, item.status == 0 ? "确认收货" : "再次兑换");
        helper.addOnClickListener(R.id.btn_more);
        if (item.type == 2) {//线下
            helper.addOnClickListener(R.id.tv_store_name);
            helper.addOnClickListener(R.id.im_store_logo);
        }
        helper.setText(R.id.tv_status, item.status == 0 ? "待收货" : "已完成");
    }
}
