package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.RScoreContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import javax.inject.Inject;


@ActivityScope
public class RScorePresenter extends BBasePresenter<IUserModel, RScoreContract.View> {

    @Inject
    public RScorePresenter(IUserModel model, RScoreContract.View rootView) {
        super(model, rootView);
    }

    public void getRScore(int billId) {
        doRequest(buildRequest(mModel.getRScore(billId)),1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what==1){
            mRootView.showRScore(((Bill) result.data));
        }
    }
}
