package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerBScoreComponent;
import com.wta.NewCloudApp.di.module.BScoreModule;
import com.wta.NewCloudApp.mvp.contract.BScoreContract;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.presenter.BScorePresenter;

import butterknife.BindView;

/**
 * 商户收款明细
 */
public class BScoreDetActivity extends BaseLoadingActivity<BScorePresenter> implements BScoreContract.View {

    @BindView(R.id.tv_top_score)
    TextView tvTopScore;
    @BindView(R.id.tv_det_type)
    TextView tvDetType;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_rec_score)
    TextView tvRecScore;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_trade_no)
    TextView tvTradeNo;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBScoreComponent
                .builder()
                .appComponent(appComponent)
                .bScoreModule(new BScoreModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bscore;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        int billId = getIntent().getIntExtra("billId", -1);
        mPresenter.getBScore(billId);
    }

    public static void startDet(Activity activity, int billId) {
        Intent intent = new Intent(activity, BScoreDetActivity.class);
        intent.putExtra("billId", billId);
        activity.startActivity(intent);
    }

    @Override
    public void showBScore(Bill bill) {
        tvTopScore.setText(bill.score);
        tvDetType.setText(bill.title);
        tvMoney.setText(bill.money);
        tvTime.setText(bill.title);
        tvTradeNo.setText(bill.ordersn);
        tvRecScore.setText(bill.recommend_score);
    }
}
