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
import com.wta.NewCloudApp.mvp.ui.widget.ClearEditText;
import com.wta.NewCloudApp.uitls.FinalUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class StoreLocDetActivity extends BaseActivity {

    @BindView(R.id.et_det)
    ClearEditText etDet;
    private String locDet;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_store_loc_det;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        locDet = getIntent().getStringExtra("locDet");
        etDet.setText(locDet);
        etDet.setSelection(etDet.getText().length());
    }
    public static void startLocDet(Activity activity, String locDet){
        Intent intent=new Intent(activity,StoreLocDetActivity.class);
        intent.putExtra("locDet",locDet);
        activity.startActivityForResult(intent, FinalUtils.REQUEST_LOC_DET);
    }

    @OnClick(R.id.btn_apply)
    public void onViewClicked() {
        String s = etDet.getText().toString();
        if (TextUtils.isEmpty(s)) ArmsUtils.makeText(this,"请输入详细地址");
        else {
            Intent intent = getIntent();
            intent.putExtra("locDet",s);
            setResult(RESULT_OK,getIntent());
            finish();
        }
    }
}
