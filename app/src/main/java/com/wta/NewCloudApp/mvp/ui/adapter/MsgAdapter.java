package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.Msg;

import java.util.List;

public class MsgAdapter extends BaseQuickAdapter<Msg,BaseViewHolder> {
    public MsgAdapter(int layoutResId, @Nullable List<Msg> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Msg item) {
        helper.setText(R.id.tv_time,item.time);
        helper.setText(R.id.tv_title,item.title);
        helper.setText(R.id.tv_content,item.description);
    }
}
