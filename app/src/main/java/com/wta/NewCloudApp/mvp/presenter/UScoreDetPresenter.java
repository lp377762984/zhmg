package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.wta.NewCloudApp.mvp.contract.UScoreDetContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.model.entity.Result;


@ActivityScope
public class UScoreDetPresenter extends BBasePresenter<IUserModel, UScoreDetContract.View> {

    @Inject
    public UScoreDetPresenter(IUserModel model, UScoreDetContract.View rootView) {
        super(model, rootView);
    }

    public void getUScore(int billId) {
        doRequest(buildRequest(mModel.getUScore(billId)),1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what==1){
            mRootView.showUScore(((Bill) result.data));
        }
    }
}
