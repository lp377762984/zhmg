package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.di.component.DaggerAddressListComponent;
import com.wta.NewCloudApp.di.module.AddressListModule;
import com.wta.NewCloudApp.jiuwei210278.R;
import com.wta.NewCloudApp.mvp.contract.AddressListContract;
import com.wta.NewCloudApp.mvp.model.entity.Address;
import com.wta.NewCloudApp.mvp.presenter.AddressListPresenter;
import com.wta.NewCloudApp.mvp.ui.adapter.AddressAdapter;
import com.wta.NewCloudApp.mvp.ui.listener.DetDialogCallback;
import com.wta.NewCloudApp.uitls.DialogUtils;
import com.wta.NewCloudApp.uitls.FinalUtils;


public class AddressListActivity extends BaseListActivity<AddressListPresenter> implements AddressListContract.View {
    private int mPosition;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddressListComponent
                .builder()
                .appComponent(appComponent)
                .addressListModule(new AddressListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_address_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        View footerView = getLayoutInflater().inflate(R.layout.address_add_footer, recyclerView, false);
        footerView.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAddressActivity.startAdd(AddressListActivity.this, null);
            }
        });
        adapter.addFooterView(footerView);
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {
                Address address = (Address) data.get(position);
                mPosition = position;
                switch (view.getId()) {
                    case R.id.item_address_del:
                        DialogUtils.showAlertDialog(AddressListActivity.this, "确定要删除吗？", new DetDialogCallback() {
                            @Override
                            public void handleRight(Dialog dialog) {
                                mPresenter.delAddress(address.address_id);
                            }
                        });
                        break;
                    case R.id.item_address_edit:
                        AddAddressActivity.startAdd(AddressListActivity.this, address);
                        break;
                    case R.id.item_address_check:
                        if (address.type == 1) return;
                        mPresenter.setDefault(address.address_id);
                        break;
                }
            }
        });
    }

    @Override
    protected void getAdapter() {
        adapter = new AddressAdapter(R.layout.address_item, data);
    }

    @Override
    public void loadData(boolean isRefresh) {
        mPresenter.getAddressList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == FinalUtils.REQUEST_ADDRESS) {
            mPresenter.getAddressList();
        }
    }

    @Override
    public boolean needLoadmore() {
        return false;
    }

    @Override
    public void setDefault() {
        for (int i = 0; i < data.size(); i++) {
            Address address = (Address) data.get(i);
            if (i == mPosition) {
                address.type = 1;
            } else {
                address.type = 0;
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void deleteSuccess() {
        data.remove(mPosition);
        adapter.notifyItemRemoved(mPosition);
    }
}
