package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.ui.widget.ClearEditText2;
import com.wta.NewCloudApp.uitls.FinalUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StorePhoneActivity extends BaseActivity {


    @BindView(R.id.et_phone)
    ClearEditText2 etPhone;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_phone);
        ButterKnife.bind(this);
        phone = getIntent().getStringExtra("phone");
        etPhone.setText(phone);
        etPhone.setSelection(etPhone.getText().length());
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
    public static void startPhone(Activity activity,String phone){
        Intent intent=new Intent(activity,StorePhoneActivity.class);
        intent.putExtra("phone",phone);
        activity.startActivityForResult(intent, FinalUtils.REQUEST_PHONE);
    }

    @OnClick(R.id.btn_apply)
    public void onViewClicked() {
        String s = etPhone.getText().toString();
        if (TextUtils.isEmpty(s)) ArmsUtils.makeText(this,"请输入客服电话");
        else {
            Intent intent = getIntent();
            intent.putExtra("phone",s);
            setResult(RESULT_OK,getIntent());
            finish();
        }
    }
}
