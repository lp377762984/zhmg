package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.AddCardContract;
import com.wta.NewCloudApp.mvp.model.UserModel;
import com.wta.NewCloudApp.mvp.model.entity.BankCard;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import javax.inject.Inject;


@ActivityScope
public class AddCardPresenter extends BBasePresenter<UserModel, AddCardContract.View> {


    @Inject
    public AddCardPresenter(UserModel model, AddCardContract.View rootView) {
        super(model, rootView);
    }

    public void addBankCard(String number) {
        doRequest(buildRequest(mModel.addBankCard(number)), 1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what==1) {
            mRootView.getBankCard((Result<BankCard>) result);
        }
    }
}
