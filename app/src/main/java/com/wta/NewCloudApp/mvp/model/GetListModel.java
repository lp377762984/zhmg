package com.wta.NewCloudApp.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.wta.NewCloudApp.mvp.contract.GetListContract;
import com.wta.NewCloudApp.mvp.model.api.HttpServices;
import com.wta.NewCloudApp.mvp.model.entity.Red;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class GetListModel extends BaseModel implements GetListContract.Model {
    private int index=1;
    @Inject
    public GetListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<Result<List<Red>>> getRedList(boolean isRefresh) {
        if (isRefresh) index=1;
        else index++;
        return mRepositoryManager.obtainRetrofitService(HttpServices.class).getRedList(index);
    }
}