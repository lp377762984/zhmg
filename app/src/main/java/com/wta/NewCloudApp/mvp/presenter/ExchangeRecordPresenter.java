package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.wta.NewCloudApp.mvp.contract.ExchangeRecordContract;


@ActivityScope
public class ExchangeRecordPresenter extends BBasePresenter<ExchangeRecordContract.Model, ExchangeRecordContract.View> {

    @Inject
    public ExchangeRecordPresenter(ExchangeRecordContract.Model model, ExchangeRecordContract.View rootView) {
        super(model, rootView);
    }
}
