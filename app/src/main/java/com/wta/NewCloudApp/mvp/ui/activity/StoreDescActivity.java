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
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StoreDescActivity extends BaseActivity {

    @BindView(R.id.et_desc)
    ClearEditText etDesc;
    private String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_desc);
        ButterKnife.bind(this);
        desc = getIntent().getStringExtra("desc");
        etDesc.setText(desc);
        etDesc.setSelection(etDesc.getText().length());
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
    public static void startDesc(Activity activity, String desc){
        Intent intent=new Intent(activity,StoreDescActivity.class);
        intent.putExtra("desc",desc);
        activity.startActivityForResult(intent, FinalUtils.REQUEST_DESC);
    }

    @OnClick(R.id.btn_apply)
    public void onViewClicked() {
        String s = etDesc.getText().toString();
        if (TextUtils.isEmpty(s)) ArmsUtils.makeText(this,"请输入店铺介绍");
        else {
            Intent intent = getIntent();
            intent.putExtra("desc",s);
            setResult(RESULT_OK,getIntent());
            finish();
        }
    }
}
