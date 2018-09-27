package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wta.NewCloudApp.R;

import java.util.List;

public class LocItemAdapter extends BaseQuickAdapter<PoiItem, BaseViewHolder> {
    public LocItemAdapter(int layoutResId, @Nullable List<PoiItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiItem item) {
        helper.setText(R.id.tv_title, item.getTitle());
        String cityName = item.getCityName();
        String adName = item.getAdName();
        String snippet = item.getSnippet();
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(cityName) && !"null".equals(cityName)) sb.append(cityName);
        if (!TextUtils.isEmpty(adName) && !"null".equals(adName)) sb.append(adName);
        if (!TextUtils.isEmpty(snippet) && !"null".equals(snippet)) sb.append(snippet);
        helper.setText(R.id.tv_desc, sb.toString());
    }
}
