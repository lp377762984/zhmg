package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.wta.NewCloudApp.mvp.contract.SGDetContract;


@FragmentScope
public class SGDetPresenter extends BBasePresenter<SGDetContract.Model, SGDetContract.View> {

    @Inject
    public SGDetPresenter(SGDetContract.Model model, SGDetContract.View rootView) {
        super(model, rootView);
    }
}
