package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.wta.NewCloudApp.mvp.contract.BSGOrderFContract;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.SGDet;

import java.util.List;

import javax.inject.Inject;


@FragmentScope
public class BSGOrderFPresenter extends BBasePresenter<BSGOrderFContract.Model, BSGOrderFContract.View> {

    @Inject
    public BSGOrderFPresenter(BSGOrderFContract.Model model, BSGOrderFContract.View rootView) {
        super(model, rootView);
    }

    public void getBSGOrder(boolean isRefresh,int status) {
        doRequest(buildListRequest(mModel.getBSGOrder(isRefresh,status)), 1);
    }

    public void confirmGetGoods(int orderId) {
        doRequest(buildRequest(mModel.confirmGetGoods(orderId)), 2);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            mRootView.getData(what, (Result<List>) result);
        }else if (what==2){
            mRootView.confirmSuccess(result);
        }
    }

    @Override
    protected boolean isActivity() {
        return false;
    }
}
