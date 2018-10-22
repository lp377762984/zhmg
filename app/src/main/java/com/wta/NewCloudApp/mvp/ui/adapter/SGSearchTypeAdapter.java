package com.wta.NewCloudApp.mvp.ui.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.BType;

import java.util.List;

public class SGSearchTypeAdapter extends BaseQuickAdapter<BType, BaseViewHolder> {
    private int selectTypeId;

    public int getSelectTypeId() {
        return selectTypeId;
    }

    public void setSelectTypeId(int selectTypeId) {
        this.selectTypeId = selectTypeId;
    }

    public SGSearchTypeAdapter(int layoutResId, @Nullable List<BType> data, int selectTypeId) {
        super(layoutResId, data);
        this.selectTypeId = selectTypeId;
    }

    @Override
    protected void convert(BaseViewHolder helper, BType item) {
        helper.setText(R.id.text, item.type_name);
        TextView text = helper.getView(R.id.text);
        if (selectTypeId == item.type_id) {
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.select);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            text.setCompoundDrawables(null, null, drawable, null);
        }else {
            text.setCompoundDrawables(null, null, null, null);
        }
    }
}
