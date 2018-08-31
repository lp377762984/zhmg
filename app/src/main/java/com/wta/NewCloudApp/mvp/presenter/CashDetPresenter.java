package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.CashDetContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import javax.inject.Inject;


@ActivityScope
public class CashDetPresenter extends BBasePresenter<IUserModel, CashDetContract.View> {

    @Inject
    public CashDetPresenter(IUserModel model, CashDetContract.View rootView) {
        super(model, rootView);
    }

    public void billDet(long billId) {
        doRequest(buildRequest(mModel.billDet(billId)), 1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            mRootView.showBillDet(((Bill) result.data));
        }
    }
}
