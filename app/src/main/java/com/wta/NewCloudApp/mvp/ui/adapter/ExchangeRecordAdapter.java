package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

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
        // TODO: 2018/10/19
        GlideArms.with(mContext).load("").into((ImageView) helper.getView(R.id.im_store_logo));
        helper.setText(R.id.tv_store_name, !TextUtils.isEmpty(item.store_name) ? item.store_name : item.title);
        GlideArms.with(mContext).load(item.goods_img).placeholder(R.mipmap.side_b_placeholder).into((ImageView) helper.getView(R.id.im_goods));
        helper.setText(R.id.tv_name, item.goods_name);
        helper.setText(R.id.tv_score, item.unit_integral);
        helper.setText(R.id.tv_total_score, item.integral);
        helper.setText(R.id.tv_total_count, item.num + "");
        helper.addOnClickListener(R.id.btn_more);
        if (item.type == 2) helper.addOnClickListener(R.id.tv_store_name);
    }
}
