package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Switch;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.di.component.DaggerAddAddressComponent;
import com.wta.NewCloudApp.di.module.AddAddressModule;
import com.wta.NewCloudApp.jiuwei210278.R;
import com.wta.NewCloudApp.mvp.contract.AddAddressContract;
import com.wta.NewCloudApp.mvp.model.entity.Address;
import com.wta.NewCloudApp.mvp.presenter.AddAddressPresenter;
import com.wta.NewCloudApp.mvp.ui.widget.EditTextHint;
import com.wta.NewCloudApp.uitls.FinalUtils;
import com.wta.NewCloudApp.uitls.RegexUtils;


import butterknife.BindView;
import butterknife.OnClick;


public class AddAddressActivity extends BaseLoadingActivity<AddAddressPresenter> implements AddAddressContract.View {

    @BindView(R.id.et_name)
    EditTextHint etName;
    @BindView(R.id.et_number)
    EditTextHint etNumber;
    @BindView(R.id.et_region)
    EditTextHint etRegion;
    @BindView(R.id.et_address)
    EditTextHint etAddress;
    @BindView(R.id.switchButton)
    Switch switchButton;
    private Address address;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddAddressComponent
                .builder()
                .appComponent(appComponent)
                .addAddressModule(new AddAddressModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_add_address;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        address = (Address) getIntent().getSerializableExtra("address");
        if (address != null) {
            etName.setText(address.consignee);
            etName.setSelection(etName.length());
            etNumber.setText(address.mobile);
            etAddress.setText(address.address_detail);
            switchButton.setChecked(address.type == 1);
        }
    }

    public static void startAdd(Activity activity, Address address) {
        Intent intent = new Intent(activity, AddAddressActivity.class);
        intent.putExtra("address", address);
        activity.startActivityForResult(intent, FinalUtils.REQUEST_ADDRESS);
    }

    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        String name = etName.getText().toString();
        String mobile = etNumber.getText().toString();
        String region = etRegion.getText().toString();
        String addresss = etAddress.getText().toString();
        if (TextUtils.isEmpty(name)) {
            showToast("请输入收货人姓名");
            return;
        }
        if (!RegexUtils.isMobile(mobile)) {
            showToast("请输入正确的收货人手机号码");
            return;
        }
        if (TextUtils.isEmpty(region)) {
            showToast("请输入所在区域");
            return;
        }
        if (TextUtils.isEmpty(addresss)) {
            showToast("请输入详细地址");
            return;
        }
        if (address==null){
            mPresenter.saveAddress(name, mobile, 1, 710682, 1106, addresss, switchButton.isChecked() ? 1 : 0);
        }else {
            mPresenter.editAddress(address.address_id,name, mobile, 1, 710682, 1106, addresss, switchButton.isChecked() ? 1 : 0);
        }

    }

    @Override
    public void showAddress(Address address) {
        Intent intent = getIntent();
        intent.putExtra("address", address);
        setResult(RESULT_OK, intent);
        finish();
    }
}
