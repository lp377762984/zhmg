package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wta.NewCloudApp.jiuwei210278.R;
import com.wta.NewCloudApp.mvp.model.entity.Bill;

import java.util.List;


public class HomeListAdapter extends BaseQuickAdapter<Bill, BaseViewHolder> {
    public HomeListAdapter(@LayoutRes int layoutResId, @Nullable List<Bill> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Bill item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_time, item.getTime());
        helper.setText(R.id.tv_content, item.getMoney());
        helper.setText(R.id.tv_type, item.getType());
    }
}
