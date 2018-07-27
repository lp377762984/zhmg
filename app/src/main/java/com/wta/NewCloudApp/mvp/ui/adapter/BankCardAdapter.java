package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.BankCard;

import java.util.List;

public class BankCardAdapter extends BaseQuickAdapter<BankCard, BaseViewHolder> {
    public BankCardAdapter(int layoutResId, @Nullable List<BankCard> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BankCard item) {
        GlideArms.with(mContext).load(item.bank_logo).into((ImageView) helper.getView(R.id.im_logo));
        helper.setText(R.id.tv_name,item.type);
        helper.setText(R.id.tv_number,"**** **** **** "+item.bank_card);
    }
}
