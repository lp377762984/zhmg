package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.wta.NewCloudApp.mvp.contract.UScoreDetContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;


@ActivityScope
public class UScoreDetPresenter extends BBasePresenter<IUserModel, UScoreDetContract.View> {

    @Inject
    public UScoreDetPresenter(IUserModel model, UScoreDetContract.View rootView) {
        super(model, rootView);
    }
}
