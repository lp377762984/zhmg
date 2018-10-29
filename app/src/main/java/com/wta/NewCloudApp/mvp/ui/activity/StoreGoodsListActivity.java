package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerStoreGoodsListComponent;
import com.wta.NewCloudApp.di.module.StoreGoodsListModule;
import com.wta.NewCloudApp.mvp.contract.StoreGoodsListContract;
import com.wta.NewCloudApp.mvp.model.entity.ScoreGoods;
import com.wta.NewCloudApp.mvp.presenter.StoreGoodsListPresenter;
import com.wta.NewCloudApp.mvp.ui.adapter.ScoreGoodsAdapter;

/**
 * 店铺礼品页
 */
public class StoreGoodsListActivity extends BaseListActivity<StoreGoodsListPresenter> implements StoreGoodsListContract.View {

    private int shopId;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStoreGoodsListComponent
                .builder()
                .appComponent(appComponent)
                .storeGoodsListModule(new StoreGoodsListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_store_goods_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        shopId = getIntent().getIntExtra("shop_id", -1);
        super.initData(savedInstanceState);
    }

    public static void start(Activity activity, int shopId) {
        Intent intent = new Intent(activity, StoreGoodsListActivity.class);
        intent.putExtra("shop_id", shopId);
        activity.startActivity(intent);
    }

    @Override
    protected void getAdapter() {
        adapter = new ScoreGoodsAdapter(0, R.layout.score_goods_item_offline, data);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ScoreGoods sg = (ScoreGoods) data.get(position);
                SGDetActivity.start(StoreGoodsListActivity.this, sg.goods_id, 2);
            }
        });
    }

    @Override
    public void loadData(boolean isRefresh) {
        mPresenter.getSGList(isRefresh, shopId);
    }
}
