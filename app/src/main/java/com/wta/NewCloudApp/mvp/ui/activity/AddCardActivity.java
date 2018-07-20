package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.di.component.DaggerAddCardComponent;
import com.wta.NewCloudApp.di.module.AddCardModule;
import com.wta.NewCloudApp.jiuwei210278.R;
import com.wta.NewCloudApp.mvp.contract.AddCardContract;
import com.wta.NewCloudApp.mvp.model.entity.BankCard;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.presenter.AddCardPresenter;
import com.wta.NewCloudApp.mvp.ui.widget.EditTextHint;
import com.wta.NewCloudApp.uitls.FinalUtils;

import butterknife.BindView;
import butterknife.OnClick;


public class AddCardActivity extends BaseLoadingActivity<AddCardPresenter> implements AddCardContract.View {

    @BindView(R.id.et_number)
    EditTextHint etNumber;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddCardComponent
                .builder()
                .appComponent(appComponent)
                .addCardModule(new AddCardModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_add_card;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.btn_apply)
    public void onViewClicked() {
        String number = etNumber.getText().toString();
        if (TextUtils.isEmpty(number)) {
            showToast("请填写银行卡号");
            return;
        }
        mPresenter.addBankCard(number);
    }

    @Override
    public void getBankCard(Result<BankCard> cardResult) {
        Intent intent = getIntent();
        intent.putExtra("bank_card", cardResult.data);
        setResult(RESULT_OK,intent);
        finish();
    }

    public static void startAdd(Activity context) {
        Intent intent = new Intent(context, AddCardActivity.class);
        context.startActivityForResult(intent, FinalUtils.REQUEST_ADD_CARD);
    }
}
