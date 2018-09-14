package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerUScoreDetComponent;
import com.wta.NewCloudApp.di.module.UScoreDetModule;
import com.wta.NewCloudApp.mvp.contract.UScoreDetContract;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.presenter.UScoreDetPresenter;

import butterknife.BindView;

/**
 * 用户消费明细
 */
public class UScoreDetActivity extends BaseLoadingActivity<UScoreDetPresenter> implements UScoreDetContract.View {

    @BindView(R.id.tv_top_score)
    TextView tvTopScore;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_store_name)
    TextView tvStoreName;
    @BindView(R.id.tv_store_type)
    TextView tvStoreType;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_trade_no)
    TextView tvTradeNo;
    @BindView(R.id.tv_det_type)
    TextView tvDetType;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUScoreDetComponent
                .builder()
                .appComponent(appComponent)
                .uScoreDetModule(new UScoreDetModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_uscore_det;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        int billId = getIntent().getIntExtra("billId", -1);
        mPresenter.getUScore(billId);
    }

    public static void startDet(Activity activity, int billId) {
        Intent intent = new Intent(activity, UScoreDetActivity.class);
        intent.putExtra("billId", billId);
        activity.startActivity(intent);
    }

    @Override
    public void showUScore(Bill bill) {
        tvTopScore.setText(bill.score);
        tvDetType.setText(bill.title);
        tvMoney.setText(bill.money);
        tvPayType.setText(bill.pay_type);
        tvStoreName.setText(bill.shop_name);
        tvStoreType.setText(bill.level_name);
        tvTime.setText(bill.time);
        tvTradeNo.setText(bill.ordersn);
    }
}
