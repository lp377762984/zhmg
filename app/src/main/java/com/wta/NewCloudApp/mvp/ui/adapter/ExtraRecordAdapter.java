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
import com.wta.NewCloudApp.mvp.model.entity.Goods2;

import java.util.List;

public class ExtraRecordAdapter extends BaseQuickAdapter<Goods2, BaseViewHolder> {
    public ExtraRecordAdapter(int layoutResId, @Nullable List<Goods2> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Goods2 item) {
        //GlideArms.with(mContext).load(item.sign_img).into((ImageView) helper.getView(R.id.im_store_logo));
        helper.setText(R.id.tv_store_name, "订单号" + item.ordersn);
        GlideArms.with(mContext).load(item.headImg).placeholder(R.mipmap.side_b_placeholder).into((ImageView) helper.getView(R.id.im_goods));
        helper.setText(R.id.tv_name, item.title);
        //set unit score
        String prrice = "￥" + item.prrice;
        TextView tvScore = helper.getView(R.id.tv_score);
        tvScore.setText(prrice);
        //set total score
        String before = "合计:";
        String toprice = "￥" + item.toprice;
        TextView tvTotalScore = helper.getView(R.id.tv_total_score);
        tvTotalScore.setText(before + toprice);

        helper.setText(R.id.tv_total_count, "共计" + item.number + "件商品");

        int status = item.express_status;
        helper.setGone(R.id.btn_more, status == 2);
        helper.addOnClickListener(R.id.btn_more);
        if (status == 1) {
            helper.setText(R.id.tv_status, "待发货");
        } else if (status == 2) {
            helper.setText(R.id.tv_status, "待收货");
        } else if (status == 3) {
            helper.setText(R.id.tv_status, "已完成");
        }
    }
}
