package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.CashDetContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;

import javax.inject.Inject;


@ActivityScope
public class CashDetPresenter extends BBasePresenter<IUserModel, CashDetContract.View> {

    @Inject
    public CashDetPresenter(IUserModel model, CashDetContract.View rootView) {
        super(model, rootView);
    }
}
