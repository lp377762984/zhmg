package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.StoreInfoContract;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.PictureC;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;


@ActivityScope
public class StoreInfoPresenter extends BBasePresenter<IBusinessModel, StoreInfoContract.View> {

    @Inject
    public StoreInfoPresenter(IBusinessModel model, StoreInfoContract.View rootView) {
        super(model, rootView);
    }

    public void getAllStoreInfo() {
        doRequest(buildRequest(mModel.getAllStoreInfo()), 1);
    }

    public void modifyStore(String shop_doorhead, String start_time, String end_time, int shop_type, String telephone, String introduction, List<PictureC> picture) {
        doRequest(buildRequest(mModel.modifyStore(shop_doorhead, start_time, end_time, shop_type, telephone, introduction, picture)),2);
    }



    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            mRootView.showStoreInfo(((Business) result.data));
        }else if (what==2){
            mRootView.saveSuccess(((Business) result.data));
        }
    }

}
