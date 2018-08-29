package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerPayOverComponent;
import com.wta.NewCloudApp.di.module.PayOverModule;
import com.wta.NewCloudApp.mvp.contract.PayOverContract;
import com.wta.NewCloudApp.mvp.model.entity.Payback;
import com.wta.NewCloudApp.mvp.presenter.PayOverPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PayOverActivity extends BaseLoadingActivity<PayOverPresenter> implements PayOverContract.View {

    @BindView(R.id.tv_success_str)
    TextView tvSuccessStr;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_name)
    TextView tvName;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPayOverComponent
                .builder()
                .appComponent(appComponent)
                .payOverModule(new PayOverModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_pay_over;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String orderId = getIntent().getStringExtra("out_trade_no");
        String type = getIntent().getStringExtra("type");
        mPresenter.checkSuccess(orderId, type);
    }

    public static void startPayStatus(Activity context, String type, String orderId) {
        Intent intent = new Intent(context, PayOverActivity.class);
        intent.putExtra("out_trade_no", orderId);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public void showPayback(Payback payback) {
        tvSuccessStr.setText(payback.msg);
        tvMoney.setText("￥" + payback.money);
        tvScore.setText("您获得" + payback.white_score + "积分");
        tvName.setText(payback.store_name);
    }

    @OnClick(R.id.btn_over)
    public void onViewClicked() {
        finish();
    }
}
