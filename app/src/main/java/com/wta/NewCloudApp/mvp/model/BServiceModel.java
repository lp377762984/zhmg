package com.wta.NewCloudApp.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.wta.NewCloudApp.mvp.contract.BServiceContract;
import com.wta.NewCloudApp.mvp.model.api.HttpServices;
import com.wta.NewCloudApp.mvp.model.entity.BEntity;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class BServiceModel extends BaseModel implements BServiceContract.Model {

    @Inject
    public BServiceModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BEntity> getBMoney() {
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).getBMoney();
    }
}