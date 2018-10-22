package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.wta.NewCloudApp.mvp.contract.ScoreShopContract;


@ActivityScope
public class ScoreShopPresenter extends BBasePresenter<ScoreShopContract.Model, ScoreShopContract.View> {

    @Inject
    public ScoreShopPresenter(ScoreShopContract.Model model, ScoreShopContract.View rootView) {
        super(model, rootView);
    }

}
