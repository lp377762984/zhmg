package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.wta.NewCloudApp.mvp.contract.StoreGoodsListContract;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import java.util.List;


@ActivityScope
public class StoreGoodsListPresenter extends BBasePresenter<StoreGoodsListContract.Model, StoreGoodsListContract.View> {

    @Inject
    public StoreGoodsListPresenter(StoreGoodsListContract.Model model, StoreGoodsListContract.View rootView) {
        super(model, rootView);
    }

    public void getSGList(boolean isRefresh, int shopId) {
        doRequest(buildListRequest(mModel.getSGList(isRefresh, shopId)), 1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            mRootView.getData(what, (Result<List>) result);
        }
    }

    @Override
    public void handleException(int what, Throwable t) {
        super.handleException(what, t);
        if (what == 1) {
            mRootView.loadFailed();
        }
    }
}
