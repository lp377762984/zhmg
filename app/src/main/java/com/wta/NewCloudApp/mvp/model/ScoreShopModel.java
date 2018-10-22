package com.wta.NewCloudApp.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.wta.NewCloudApp.mvp.contract.ScoreShopContract;

import javax.inject.Inject;


@ActivityScope
public class ScoreShopModel extends BaseModel implements ScoreShopContract.Model {

    @Inject
    public ScoreShopModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
}