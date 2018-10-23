package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.ScoreGoods;

import java.util.List;

public class ScoreGoodsAdapter extends BaseQuickAdapter<ScoreGoods, BaseViewHolder> {
    private int position;//1线上 2线下

    public ScoreGoodsAdapter(int position, int layoutResId, @Nullable List<ScoreGoods> data) {
        super(layoutResId, data);
        this.position = position;
    }

    @Override
    protected void convert(BaseViewHolder helper, ScoreGoods item) {
        GlideArms.with(mContext).load(item.avatar).placeholder(R.mipmap.side_b_placeholder)
                .into((ImageView) helper.getView(R.id.im_icon));
        //set score start
        String score = item.integral;
        String scoTrs = " 积分";
        SpannableStringBuilder ss = new SpannableStringBuilder(score + scoTrs);
        ss.setSpan(new RelativeSizeSpan(0.8f), score.length(), ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(0x808080), score.length(), ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        TextView tvScore = helper.getView(R.id.tv_score);
        tvScore.setText(ss);

        helper.setText(R.id.tv_name, item.title);
        if (position == 2) {
            helper.setText(R.id.tv_b_name, item.shop_name + "(" + item.location_address + item.address_details + ")");
            helper.setText(R.id.tv_distance, item.distance);
        }
    }
}
