package com.wta.NewCloudApp.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.wta.NewCloudApp.mvp.contract.ExRecDetContract;
import com.wta.NewCloudApp.mvp.model.api.HttpServices;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.SGDet;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class ExRecDetModel extends BaseModel implements ExRecDetContract.Model {

    @Inject
    public ExRecDetModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Result<SGDet>> getExRecDet(int orderId) {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).getExRecDet(orderId);
    }

    @Override
    public Observable<Result> confirmGetGoods(int orderId) {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).sureGetGift(orderId);
    }
}