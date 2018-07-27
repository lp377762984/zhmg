package com.wta.NewCloudApp.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.Address;

import java.util.List;

public class AddressAdapter extends BaseQuickAdapter<Address,BaseViewHolder> {
    public AddressAdapter(int layoutResId, @Nullable List<Address> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Address item) {
        helper.setText(R.id.item_address_name, item.consignee);
        helper.setText(R.id.item_address_phone, item.mobile);
        helper.setText(R.id.item_address_tv, item.address_detail);
        helper.setChecked(R.id.item_address_check, item.type == 1);
        helper.getView(R.id.item_address_check).setEnabled(item.type == 0);
        helper.setText(R.id.item_address_check, item.type == 0 ? "设为默认" : "默认地址");
        helper.addOnClickListener(R.id.item_address_del);
        helper.addOnClickListener(R.id.item_address_edit);
        helper.addOnClickListener(R.id.item_address_check);
    }
}
