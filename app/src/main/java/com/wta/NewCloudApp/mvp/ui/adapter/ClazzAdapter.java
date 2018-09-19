package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.UserClass;

import java.util.List;

public class ClazzAdapter extends BaseQuickAdapter<UserClass, BaseViewHolder> {
    public ClazzAdapter(int layoutResId, @Nullable List<UserClass> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserClass item) {//im_bg
        GlideArms.with(mContext).load(item.classLogo).into((ImageView) helper.getView(R.id.im_class_logo));
        GlideArms.with(mContext).load(item.bg).into((ImageView) helper.getView(R.id.im_bg));
        helper.setText(R.id.tv_class, item.clazz);
        helper.setText(R.id.tv_money, item.money);
        helper.setText(R.id.tv_desc, item.desc);
    }
}
