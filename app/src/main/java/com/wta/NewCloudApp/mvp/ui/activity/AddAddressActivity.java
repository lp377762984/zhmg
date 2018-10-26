package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.di.component.DaggerAddAddressComponent;
import com.wta.NewCloudApp.di.module.AddAddressModule;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.contract.AddAddressContract;
import com.wta.NewCloudApp.mvp.model.entity.Address;
import com.wta.NewCloudApp.mvp.model.entity.Province;
import com.wta.NewCloudApp.mvp.presenter.AddAddressPresenter;
import com.wta.NewCloudApp.mvp.ui.widget.EditTextHint;
import com.wta.NewCloudApp.mvp.ui.widget.MoneyBar;
import com.wta.NewCloudApp.uitls.FinalUtils;
import com.wta.NewCloudApp.uitls.KeyBoardUtils;
import com.wta.NewCloudApp.uitls.RegexUtils;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class AddAddressActivity extends BaseLoadingActivity<AddAddressPresenter> implements AddAddressContract.View {

    @BindView(R.id.et_name)
    EditTextHint etName;
    @BindView(R.id.et_number)
    EditTextHint etNumber;
    @BindView(R.id.tv_region)
    TextView tvRegion;
    @BindView(R.id.et_address)
    EditTextHint etAddress;
    @BindView(R.id.switchButton)
    Switch switchButton;
    @BindView(R.id.mb)
    MoneyBar mb;
    private Address address;
    private boolean hasAddress;
    private ArrayList<Province> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<Province.City>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<Province.City.District>>> options3Items = new ArrayList<>();

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
        mPresenter.parseLocalAddressData();
        address = (Address) getIntent().getSerializableExtra("address");
        if (address != null) {
            hasAddress = true;
            etName.setText(address.consignee);
            etName.setSelection(etName.length());
            etNumber.setText(address.mobile);
            tvRegion.setText(address.address_region);
            etAddress.setText(address.address);
            switchButton.setChecked(address.type == 1);
            mb.setTextTail("删除");
            mb.setCallBack(mb.new CallbackImp() {
                @Override
                public void clickTail() {
                    mPresenter.delAddress(address.address_id);
                }
            });
        } else {
            hasAddress = false;
            mb.setTextTail("");
        }
    }

    public static void startAdd(Activity activity, Address address) {
        Intent intent = new Intent(activity, AddAddressActivity.class);
        intent.putExtra("address", address);
        activity.startActivityForResult(intent, FinalUtils.REQUEST_ADDRESS);
    }

    @OnClick({R.id.btn_save, R.id.tv_region})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                String name = etName.getText().toString();
                String mobile = etNumber.getText().toString();
                String region = tvRegion.getText().toString();
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
                if (!hasAddress) {
                    mPresenter.saveAddress(name, mobile, address.province, address.city, address.district, addresss, switchButton.isChecked() ? 1 : 0);
                } else {
                    mPresenter.editAddress(address.address_id, name, mobile, address.province, address.city, address.district, addresss, switchButton.isChecked() ? 1 : 0);
                }
                break;
            case R.id.tv_region:
                KeyBoardUtils.hideKeyBoard(this);
                if (options1Items.size() == 0) {
                    showToast("数据正在初始化");
                    return;
                }
                OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        Province province = options1Items.get(options1);
                        Province.City city = options2Items.get(options1).get(options2);
                        Province.City.District district = options3Items.get(options1).get(options2).get(options3);
                        if (address == null) address = new Address();
                        address.province = province.getId();
                        address.city = city.getId();
                        address.district = district.getId();
                        String tx = province.getPickerViewText() + " " + city.getPickerViewText() + " " + district.getPickerViewText();
                        tvRegion.setText(tx);
                    }
                })

                        .setTitleText("城市选择")
                        .setDividerColor(Color.GRAY)
                        .setTextColorCenter(Color.GRAY) //设置选中项文字颜色
                        .setContentTextSize(15)
                        .setCancelText("取消")
                        .setSubmitText("确定")
                        .build();
                pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
                if (address != null) {
                    int selects[] = calculateSelect();
                    pvOptions.setSelectOptions(selects[0], selects[1], selects[2]);
                }
                pvOptions.show();
                break;
        }
    }

    private int[] calculateSelect() {
        int[] selects = new int[3];
        for (int i = 0; i < options1Items.size(); i++) {
            if (options1Items.get(i).getId() == address.province) {
                selects[0] = i;
                break;
            }
        }
        ArrayList<Province.City> cities = options2Items.get(selects[0]);
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getId() == address.city) {
                selects[1] = i;
                break;
            }
        }
        ArrayList<Province.City.District> districts = options3Items.get(selects[0]).get(selects[1]);
        for (int i = 0; i < districts.size(); i++) {
            if (districts.get(i).getId() == address.district) {
                selects[2] = i;
            }
        }
        return selects;
    }

    @Override
    public void showAddress(Address address) {
        Intent intent = getIntent();
        intent.putExtra("address", address);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void deleteSuccess() {
        setResult(RESULT_OK, getIntent());
        finish();
    }

    @Override
    public void initAddressSuccess(ArrayList<Province> provinces) {
        this.options1Items.addAll(provinces);
        for (int i = 0; i < provinces.size(); i++) {//所有省
            Province province = provinces.get(i);
            ArrayList<Province.City> citys = province.getCitys();
            options2Items.add(citys);
            ArrayList<ArrayList<Province.City.District>> diss = new ArrayList<>();
            for (int j = 0; j < citys.size(); j++) {//所有市
                Province.City city = citys.get(j);
                ArrayList<Province.City.District> districts = city.getDistricts();
                diss.add(districts);
            }
            options3Items.add(diss);
        }
    }
}
