package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.di.component.DaggerAddRecComponent;
import com.wta.NewCloudApp.di.component.DaggerGroupComponent;
import com.wta.NewCloudApp.di.module.AddRecModule;
import com.wta.NewCloudApp.di.module.GroupModule;
import com.wta.NewCloudApp.jiuwei210278.R;
import com.wta.NewCloudApp.mvp.contract.AddRecContract;
import com.wta.NewCloudApp.mvp.presenter.AddRecPresenter;
import com.wta.NewCloudApp.mvp.ui.widget.EditTextHint;
import com.wta.NewCloudApp.uitls.FinalUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class AddRecActivity extends BaseLoadingActivity<AddRecPresenter> implements AddRecContract.View {

    @BindView(R.id.et_rec_code)
    EditTextHint etRecCode;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddRecComponent
                .builder()
                .appComponent(appComponent)
                .addRecModule(new AddRecModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_add_rec;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        String code = etRecCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            showToast("请输入推荐码");
            return;
        }
        mPresenter.setRecCode(code);
    }

    public static void start(Activity context) {
        Intent intent = new Intent(context, AddRecActivity.class);
        context.startActivityForResult(intent, FinalUtils.REQUEST_REC_CODE);
    }

    @Override
    public void refresh() {
        Intent intent = getIntent();
        intent.putExtra("rec_code", etRecCode.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
