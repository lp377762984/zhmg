package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.ScoreGoods;

import java.util.List;

public class ScoreGoodsAdapter extends BaseQuickAdapter<ScoreGoods, BaseViewHolder> {
    private int position;

    public ScoreGoodsAdapter(int position, int layoutResId, @Nullable List<ScoreGoods> data) {
        super(layoutResId, data);
        this.position = position;
    }

    @Override
    protected void convert(BaseViewHolder helper, ScoreGoods item) {
        GlideArms.with(mContext).load(item.avatar).placeholder(R.mipmap.side_b_placeholder)
                .into((ImageView) helper.getView(R.id.im_icon));
        helper.setText(R.id.tv_name, item.title);
        helper.setText(R.id.tv_score, item.integral);
    }
}
