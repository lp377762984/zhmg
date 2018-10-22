package com.wta.NewCloudApp.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.wta.NewCloudApp.mvp.contract.StoreGoodsListContract;
import com.wta.NewCloudApp.mvp.model.api.HttpServices;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.ScoreGoods;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class StoreGoodsListModel extends BaseModel implements StoreGoodsListContract.Model {
    private int index;

    @Inject
    public StoreGoodsListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Result<List<ScoreGoods>>> getSGList(boolean isRefresh, int shopId) {
        if (isRefresh) index = 1;
        else index++;
        if (shopId == -1)
            return mRepositoryManager.obtainRetrofitService(HttpServices.class).getSGListSelf(index);
        else return mRepositoryManager.obtainRetrofitService(HttpServices.class).getSGList(shopId, index);
    }
}