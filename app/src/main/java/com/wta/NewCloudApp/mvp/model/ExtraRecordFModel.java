package com.wta.NewCloudApp.mvp.model;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.wta.NewCloudApp.mvp.contract.ExtraRecordFContract;
import com.wta.NewCloudApp.mvp.model.api.HttpServices;
import com.wta.NewCloudApp.mvp.model.entity.Goods2;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.ScoreGoods;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@FragmentScope
public class ExtraRecordFModel extends BaseModel implements ExtraRecordFContract.Model {
    private int index;

    @Inject
    public ExtraRecordFModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Result<List<Goods2>>> getExtraRecordList(int status, boolean isRefresh) {
        if (isRefresh) index = 1;
        else index++;
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).getExtraRecordList(status, index);
    }

    @Override
    public Observable<Result> sureGetGoods(int orderId) {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).sureGetGoods(orderId);
    }

}