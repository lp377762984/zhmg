package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.PictureC;

import java.util.List;

public class PictureAdapter extends BaseQuickAdapter<PictureC, BaseViewHolder> {
    public PictureAdapter(int layoutResId, @Nullable List<PictureC> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PictureC item) {
        if (!TextUtils.isEmpty(item.url))
            GlideArms.with(mContext).load(item.url).placeholder(R.mipmap.side_b_placeholder).into((ImageView) helper.getView(R.id.im));
        else  GlideArms.with(mContext).load(item.file).placeholder(R.mipmap.side_b_placeholder).into((ImageView) helper.getView(R.id.im));
        helper.addOnClickListener(R.id.im_close);
    }
}
