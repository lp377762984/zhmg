package com.wta.NewCloudApp.mvp.model;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.wta.NewCloudApp.mvp.contract.ScoreGoodsListContract;
import com.wta.NewCloudApp.mvp.model.api.HttpServices;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.SG2;
import com.wta.NewCloudApp.mvp.model.entity.ScoreGoods;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@FragmentScope
public class ScoreGoodsListModel extends BaseModel implements ScoreGoodsListContract.Model {
    private int index;

    @Inject
    public ScoreGoodsListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Result<SG2>> getScoreGoods(int position, int type, String keywords, Double lat, Double lon, boolean isRefresh) {
        if (isRefresh) index = 1;
        else index++;
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).getScoreGoods(position, type, keywords, lat, lon,index);
    }
}