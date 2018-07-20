package com.wta.NewCloudApp.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.di.component.DaggerCardListComponent;
import com.wta.NewCloudApp.di.module.CardListModule;
import com.wta.NewCloudApp.jiuwei210278.R;
import com.wta.NewCloudApp.mvp.contract.CardListContract;
import com.wta.NewCloudApp.mvp.model.entity.BankCard;
import com.wta.NewCloudApp.mvp.presenter.CardListPresenter;
import com.wta.NewCloudApp.mvp.ui.adapter.BankCardAdapter;
import com.wta.NewCloudApp.uitls.FinalUtils;


public class CardListActivity extends BaseListActivity<CardListPresenter> implements CardListContract.View, View.OnClickListener {
    private int delPosition;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCardListComponent
                .builder()
                .appComponent(appComponent)
                .cardListModule(new CardListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_card_list;
    }

    @Override
    protected void getAdapter() {
        adapter = new BankCardAdapter(R.layout.card_item, data);

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        View footView = getLayoutInflater().inflate(R.layout.card_foot, recyclerView, false);
        footView.setOnClickListener(this);
        adapter.addFooterView(footView);
        recyclerView.addOnItemTouchListener(new OnItemLongClickListener() {
            @Override
            public void onSimpleItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                delPosition = position;
                mPresenter.deleteCard(((BankCard) data.get(position)).id);
            }
        });
    }

    @Override
    public void loadData(boolean isRefresh) {
        mPresenter.getCardList(isRefresh);
    }

    @Override
    public void onClick(View v) {
        AddCardActivity.startAdd(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == FinalUtils.REQUEST_ADD_CARD) {
            updateList((BankCard) data.getSerializableExtra("bank_card"));
        }
    }

    public void updateList(BankCard cardResult) {
        data.add(cardResult);
        adapter.notifyItemRangeInserted(data.size(), 1);
    }

    @Override
    public void deleteSuccess() {
        adapter.notifyItemRemoved(delPosition);
    }
}
