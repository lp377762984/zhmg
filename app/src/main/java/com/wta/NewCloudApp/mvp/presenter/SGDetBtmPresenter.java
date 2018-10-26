package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.wta.NewCloudApp.mvp.contract.SGDetBtmContract;

import javax.inject.Inject;


@FragmentScope
public class SGDetBtmPresenter extends BasePresenter<SGDetBtmContract.Model, SGDetBtmContract.View> {

    @Inject
    public SGDetBtmPresenter(SGDetBtmContract.Model model, SGDetBtmContract.View rootView) {
        super(model, rootView);
    }

}
