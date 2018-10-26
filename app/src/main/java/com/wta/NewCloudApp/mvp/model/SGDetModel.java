package com.wta.NewCloudApp.mvp.model;


import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.wta.NewCloudApp.mvp.contract.SGDetContract;
import com.wta.NewCloudApp.mvp.model.api.HttpServices;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.SGDet;
import com.wta.NewCloudApp.mvp.model.entity.SGOrder;

import javax.inject.Inject;

import io.reactivex.Observable;


@FragmentScope
public class SGDetModel extends BaseModel implements SGDetContract.Model {

    @Inject
    public SGDetModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Result<SGDet>> getSGDet(int type, int goodsId) {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).getSGDet(type, goodsId);
    }

    @Override
    public Observable<Result<SGOrder>> exchange(int type, int goodsId, int currentNum, int addressId) {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).exchange(type, goodsId,currentNum,addressId);
    }
}