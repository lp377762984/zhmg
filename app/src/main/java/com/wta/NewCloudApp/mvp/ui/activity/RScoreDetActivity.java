package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerRScoreComponent;
import com.wta.NewCloudApp.di.module.RScoreModule;
import com.wta.NewCloudApp.mvp.contract.RScoreContract;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.presenter.RScorePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 推荐明细
 */
public class RScoreDetActivity extends BaseLoadingActivity<RScorePresenter> implements RScoreContract.View {

    @BindView(R.id.tv_top_score)
    TextView tvTopScore;
    @BindView(R.id.tv_det_type)
    TextView tvDetType;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_time)
    TextView tvTime;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRScoreComponent
                .builder()
                .appComponent(appComponent)
                .rScoreModule(new RScoreModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_rscore;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        int billId = getIntent().getIntExtra("billId", -1);
        mPresenter.getRScore(billId);
    }

    public static void startDet(Activity activity, int billId) {
        Intent intent = new Intent(activity, RScoreDetActivity.class);
        intent.putExtra("billId", billId);
        activity.startActivity(intent);
    }

    @Override
    public void showRScore(Bill bill) {
        tvTopScore.setText(bill.score);
        tvDetType.setText(bill.title);
        tvMoney.setText(bill.money);
        tvTime.setText(bill.time);
    }
}
