package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;

import butterknife.OnClick;

/**
 * 兑换成功
 */
public class ExSucActivity extends BaseActivity {

    private int orderId;
    private int type;
    private int status;
    private int goodsId;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_ex_suc;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        orderId = getIntent().getIntExtra("order_id", -1);
        type = getIntent().getIntExtra("type", -1);
        status = getIntent().getIntExtra("status", -1);
        goodsId = getIntent().getIntExtra("goods_id", -1);
    }

    public static void start(Activity activity, int orderId, int type, int status, int goodsId) {
        Intent intent = new Intent(activity, ExSucActivity.class);
        intent.putExtra("order_id", orderId);
        intent.putExtra("type", type);
        intent.putExtra("status", status);
        intent.putExtra("goods_id", goodsId);
        activity.startActivity(intent);
    }

    @OnClick({R.id.btn_re, R.id.btn_sure, R.id.im_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_re:
                SGDetActivity.start(this, goodsId, type);
                finish();
                break;
            case R.id.im_back:
                finish();
                break;
            case R.id.btn_sure:
                ExRecDetActivity.start(this, orderId, type, status);
                finish();
                break;
        }
    }
}
