package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerCashDetComponent;
import com.wta.NewCloudApp.di.module.CashDetModule;
import com.wta.NewCloudApp.mvp.contract.CashDetContract;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.presenter.CashDetPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 收款明细
 */
public class CashDetActivity extends BaseLoadingActivity<CashDetPresenter> implements CashDetContract.View {

    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_service_fee)
    TextView tvServiceFee;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_trade_no)
    TextView tvTradeNo;
    private long billId;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCashDetComponent
                .builder()
                .appComponent(appComponent)
                .cashDetModule(new CashDetModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_cash_det;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        billId = getIntent().getLongExtra("bill_id", -1);
        mPresenter.billDet(billId);
    }

    public static void startCashDet(Activity activity, long billID) {
        Intent intent = new Intent(activity, CashDetActivity.class);
        intent.putExtra("bill_id", billID);
        activity.startActivity(intent);
    }

    @Override
    public void showBillDet(Bill bill) {
        tvClear.setText(bill.platform_money);
        tvTotalPrice.setText(bill.money);
        tvServiceFee.setText(bill.business_money);
        tvScore.setText(bill.business_white_score);
        tvTradeNo.setText(bill.ordersn);
        tvPayType.setText(bill.pay_type);
        tvTime.setText(bill.time);
    }
}
