package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.jiuwei210278.R;
import com.wta.NewCloudApp.mvp.ui.widget.EditTextHint;
import com.wta.NewCloudApp.uitls.FinalUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class NameSetActivity extends BaseActivity {

    @BindView(R.id.et_name)
    EditTextHint etName;
    @BindView(R.id.btn_save)
    Button btnSave;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_name_set;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String name = getIntent().getStringExtra("name");
        if (!TextUtils.isEmpty(name)) {
            etName.setText(name);
            etName.setSelection(name.length());
        }
    }

    public static void start(Activity context, String name) {
        Intent intent = new Intent(context, NameSetActivity.class);
        intent.putExtra("name", name);
        context.startActivityForResult(intent, FinalUtils.REQUEST_SET_NAME);
    }

    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        Intent intent = getIntent();
        intent.putExtra("name", etName.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
