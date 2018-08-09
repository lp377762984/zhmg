package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.wta.NewCloudApp.mvp.contract.MerchantTypeContract;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;
import com.wta.NewCloudApp.mvp.model.entity.BType;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import java.util.List;


@ActivityScope
public class MerchantTypePresenter extends BBasePresenter<IBusinessModel, MerchantTypeContract.View> {

    @Inject
    public MerchantTypePresenter(IBusinessModel model, MerchantTypeContract.View rootView) {
        super(model, rootView);
    }

    public void getTypes(){
        doRequest(buildRequest(mModel.getBTypeList()),1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what==1){
            mRootView.showBTypeList((List<BType>) result.data);
        }
    }
}
