package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.wta.NewCloudApp.mvp.contract.HomeContract;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.HomeBanner;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import java.util.List;

import javax.inject.Inject;


@FragmentScope
public class HomePresenter extends BBasePresenter<HomeContract.Model, HomeContract.View> {

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }

    public void getMsgList() {
        doRequest(buildRequest(false, mModel.getBillList(), true), 1);
    }

    public void getStoreState() {
        doRequest(buildRequest(mModel.getStoreState()), 2);
    }

    public void getHomeBanner() {
        doRequest(buildRequest(mModel.getHomeBanner()), 3);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            mRootView.showList((Result<List<Bill>>) result);
        } else if (what == 2) {
            mRootView.showBState((Result<Business>) result);
        } else if (what == 3){
            mRootView.showHomeBanner(((List<HomeBanner>) result.data));
        }
    }
}
