package com.wta.NewCloudApp.mvp.model;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.wta.NewCloudApp.mvp.contract.ExchangeRecordFContract;
import com.wta.NewCloudApp.mvp.model.api.HttpServices;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.ScoreGoods;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@FragmentScope
public class ExchangeRecordFModel extends BaseModel implements ExchangeRecordFContract.Model {
    private int index;

    @Inject
    public ExchangeRecordFModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Result<List<ScoreGoods>>> getExchangeRec(int status, boolean isRefresh) {
        if (isRefresh) index = 1;
        else index++;
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).getExchangeRec(status, index);
    }

    @Override
    public Observable<Result> sureGetGift(int orderId) {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).sureGetGift(orderId);
    }
}