package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.CashGetContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;

import javax.inject.Inject;


@ActivityScope
public class CashGetPresenter extends BBasePresenter<IUserModel, CashGetContract.View> {

    @Inject
    public CashGetPresenter(IUserModel model, CashGetContract.View rootView) {
        super(model, rootView);
    }
}
