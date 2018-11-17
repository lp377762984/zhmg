package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.wta.NewCloudApp.mvp.contract.GetListContract;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import java.util.List;


@ActivityScope
public class GetListPresenter extends BBasePresenter<GetListContract.Model, GetListContract.View> {

    @Inject
    public GetListPresenter(GetListContract.Model model, GetListContract.View rootView) {
        super(model, rootView);
    }

    public void getRedList(boolean isRefresh) {
        doRequest(buildListRequest(mModel.getRedList(isRefresh)),1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        mRootView.getData(what, (Result<List>) result);
    }

    @Override
    public void handleException(int what, Throwable t) {
        super.handleException(what, t);
        mRootView.loadFailed();
    }
}
