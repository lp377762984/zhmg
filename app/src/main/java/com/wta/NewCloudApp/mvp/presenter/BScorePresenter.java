package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.BScoreContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import javax.inject.Inject;


@ActivityScope
public class BScorePresenter extends BBasePresenter<IUserModel, BScoreContract.View> {

    @Inject
    public BScorePresenter(IUserModel model, BScoreContract.View rootView) {
        super(model, rootView);
    }

    public void getBScore(int billId) {
        doRequest(buildRequest(mModel.getBScore(billId)),1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what==1){
            mRootView.showBScore(((Bill) result.data));
        }
    }
}
