package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.wta.NewCloudApp.mvp.contract.ScoreShopContract;
import com.wta.NewCloudApp.mvp.model.entity.BType;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import java.util.List;


@ActivityScope
public class ScoreShopPresenter extends BBasePresenter<ScoreShopContract.Model, ScoreShopContract.View> {

    @Inject
    public ScoreShopPresenter(ScoreShopContract.Model model, ScoreShopContract.View rootView) {
        super(model, rootView);
    }

    public void getSearchTypeList() {
        doRequest(buildRequest(false, mModel.getSearchTypeList(), false), 1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            mRootView.showTypeList(((List<BType>) result.data));
        }
    }

}
