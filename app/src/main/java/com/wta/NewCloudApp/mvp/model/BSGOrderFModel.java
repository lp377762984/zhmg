package com.wta.NewCloudApp.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.wta.NewCloudApp.mvp.contract.BSGOrderFContract;
import com.wta.NewCloudApp.mvp.model.api.HttpServices;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.SGDet;

import java.util.List;

import io.reactivex.Observable;


@FragmentScope
public class BSGOrderFModel extends BaseModel implements BSGOrderFContract.Model {
    private int index;

    @Inject
    public BSGOrderFModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Result<List<SGDet>>> getBSGOrder(boolean isRefresh, int status) {
        if (isRefresh) index = 1;
        else index++;
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).getBSGOrder(status,index);
    }

    @Override
    public Observable<Result> confirmGetGoods(int orderId) {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).confirmGetGoods(orderId);
    }
}