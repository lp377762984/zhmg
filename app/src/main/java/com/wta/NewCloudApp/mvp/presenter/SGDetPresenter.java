package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.wta.NewCloudApp.mvp.contract.SGDetContract;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.SGDet;
import com.wta.NewCloudApp.mvp.model.entity.SGOrder;


@FragmentScope
public class SGDetPresenter extends BBasePresenter<SGDetContract.Model, SGDetContract.View> {

    @Inject
    public SGDetPresenter(SGDetContract.Model model, SGDetContract.View rootView) {
        super(model, rootView);
    }

    public void getSGDet(int type, int goodsId) {
        doRequest(buildRequest(mModel.getSGDet(type, goodsId)), 1);
    }

    public void exchange(int type, int goodsId, int currentNum, int addressId) {
        doRequest(buildRequest(mModel.exchange(type, goodsId, currentNum, addressId)), 2);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            mRootView.showSGDet(((SGDet) result.data));
        }else if (what==2){
            mRootView.showOrderId(((SGOrder) result.data).order_id);
        }
    }

    @Override
    public void handleExcept200(int what, Result rt) {
        super.handleExcept200(what, rt);
        if (what == 1) {
            mRootView.killMyself();
        }
    }

    @Override
    public void handleException(int what, Throwable t) {
        super.handleException(what, t);
        if (what == 1) {
            mRootView.killMyself();
        }
    }

    @Override
    protected boolean isActivity() {
        return false;
    }
}
