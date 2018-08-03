package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerMerchantInfoComponent;
import com.wta.NewCloudApp.di.module.MerchantInfoModule;
import com.wta.NewCloudApp.mvp.contract.MerchantInfoContract;
import com.wta.NewCloudApp.mvp.presenter.MerchantInfoPresenter;
import com.wta.NewCloudApp.mvp.ui.widget.EditTextHint;

import butterknife.BindView;
import butterknife.OnClick;


public class MerchantInfoActivity extends BaseLoadingActivity<MerchantInfoPresenter> implements MerchantInfoContract.View {

    @BindView(R.id.et_name)
    EditTextHint etName;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.ty_type)
    TextView tvType;
    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_loc_4)
    TextView tvLoc4;
    @BindView(R.id.im_head)
    ImageView imHead;
    @BindView(R.id.checkBox)
    CheckBox checkBox;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMerchantInfoComponent
                .builder()
                .appComponent(appComponent)
                .merchantInfoModule(new MerchantInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_merchant_info;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.lat_name, R.id.lat_start_time, R.id.lat_end_time, R.id.lat_type, R.id.lat_class, R.id.lat_location, R.id.lat_loc_4, R.id.btn_apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lat_name:
                break;
            case R.id.lat_start_time:
                break;
            case R.id.lat_end_time:
                break;
            case R.id.lat_type:
                break;
            case R.id.lat_class:
                break;
            case R.id.lat_location:
                break;
            case R.id.lat_loc_4:
                break;
            case R.id.btn_apply:
                if (TextUtils.isEmpty(etName.getText())){
                    showToast("请输入商户名称");
                    return;
                }
                if (TextUtils.isEmpty(tvStartTime.getText())){
                    showToast("请选择开始时间");
                    return;
                }
                if (TextUtils.isEmpty(tvEndTime.getText())){
                    showToast("请选择结束时间");
                    return;
                }
                if (TextUtils.isEmpty(tvType.getText())){
                    showToast("请选择主营类目");
                    return;
                }
                if (TextUtils.isEmpty(tvClass.getText())){
                    showToast("请选择店铺级别");
                    return;
                }
                if (TextUtils.isEmpty(tvLocation.getText())){
                    showToast("没有店铺定位");
                    return;
                }
                if (TextUtils.isEmpty(tvLoc4.getText())){
                    showToast("请选择所在区县");
                    return;
                }

                break;
        }
    }
}
