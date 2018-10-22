package com.wta.NewCloudApp.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerScoreGoodsListComponent;
import com.wta.NewCloudApp.di.module.ScoreGoodsListModule;
import com.wta.NewCloudApp.manager.LocationManager;
import com.wta.NewCloudApp.mvp.contract.ScoreGoodsListContract;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.SG2;
import com.wta.NewCloudApp.mvp.model.entity.ScoreGoods;
import com.wta.NewCloudApp.mvp.presenter.ScoreGoodsListPresenter;
import com.wta.NewCloudApp.mvp.ui.activity.ScoreShopActivity;
import com.wta.NewCloudApp.mvp.ui.adapter.ScoreGoodsAdapter;

import java.util.List;

import timber.log.Timber;


public class ScoreGoodsListFragment extends BaseListFragment<ScoreGoodsListPresenter> implements ScoreGoodsListContract.View, LocationManager.LocateListener {

    private int position;
    private int type;
    private String keywords;
    private Double longitude;
    private Double latitude;
    private LocationManager manager;
    private boolean isLocate;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerScoreGoodsListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .scoreGoodsListModule(new ScoreGoodsListModule(this))
                .build()
                .inject(this);
    }

    public static ScoreGoodsListFragment getInstance(int position, String keywords, int type) {
        ScoreGoodsListFragment fragment = new ScoreGoodsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putInt("type", type);
        bundle.putString("keywords", keywords);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_score_goods_list, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        startLocation();
        position = getArguments().getInt("position");
        type = getArguments().getInt("type");
        keywords = getArguments().getString("keywords");
        super.initData(savedInstanceState);
    }

    @Override
    protected void getAdapter() {
        if (position == 2)
            adapter = new ScoreGoodsAdapter(position, R.layout.score_goods_item_offline, data);
        else adapter = new ScoreGoodsAdapter(position, R.layout.score_goods_item_online, data);
    }

    @Override
    public void loadData(boolean isRefresh) {
        request();
    }

    private void request() {
        if (position == 2) {
            if (isLocate)
                mPresenter.getScoreGoods(position, type, keywords, latitude, longitude, isRefresh);
        } else {
            mPresenter.getScoreGoods(position, type, keywords, null, null, isRefresh);
        }
    }

    private void startLocation() {
        if (manager == null) {
            manager = new LocationManager(getActivity(), this);
        }
        manager.start();
    }

    @Override
    public void onLocateSuccess(AMapLocation location) {
        Timber.i("onLocateSuccess: ");
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        if (!isLocate)
            mPresenter.getScoreGoods(position, type, keywords, latitude, longitude, isRefresh);
        isLocate = true;

    }

    @Override
    public boolean onLocateFailed(AMapLocation location) {
        Timber.i("onLocateFailed: ");
        if (!isLocate)
            mPresenter.getScoreGoods(position, type, keywords, latitude, longitude, isRefresh);
        isLocate = true;
        return false;
    }

    @Override
    public void noPermission() {
        Timber.i("noPermission: ");
        if (!isLocate)
            mPresenter.getScoreGoods(position, type, keywords, latitude, longitude, isRefresh);
        isLocate = true;
    }

    @Override
    public void getData(int what, Result<List> result) {

    }

    @Override
    public void getMulti(int what, SG2 sg2) {
        ((ScoreShopActivity) getActivity()).setScore(sg2.score);
        List<ScoreGoods> scoreGoods = sg2.list;
        if (isRefresh) {
            adapter.setNewData(scoreGoods);
        } else {
            if (scoreGoods == null || scoreGoods.size() == 0) isComplete = true;
            else adapter.addData(scoreGoods);
        }
    }

    public void refresh() {
        recyclerView.scrollToPosition(0);
        isRefresh = true;
        loadData(isRefresh);
    }
}
