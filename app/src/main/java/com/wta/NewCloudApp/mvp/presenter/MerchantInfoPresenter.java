package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.MerchantInfoContract;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;
import com.wta.NewCloudApp.mvp.model.entity.BClass;
import com.wta.NewCloudApp.mvp.model.entity.BType;
import com.wta.NewCloudApp.mvp.model.entity.ErrorBusiness;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import java.util.List;

import javax.inject.Inject;


@ActivityScope
public class MerchantInfoPresenter extends BBasePresenter<IBusinessModel, MerchantInfoContract.View> {

    @Inject
    public MerchantInfoPresenter(IBusinessModel model, MerchantInfoContract.View rootView) {
        super(model, rootView);
    }

    public void addStoreInfo(String name, int shop_type, int shop_level, double shop_address_x, double shop_address_y, String start_time, String end_time,
                             String shop_door_head, int province, int city, int district, int town, String location_address, String address) {
        doRequest(buildRequest(mModel.addStoreInfo(name, shop_type, shop_level, shop_address_x, shop_address_y, start_time,
                end_time, shop_door_head, province, city, district, town, location_address, address)), 1);
    }

    public void getBClass() {
        doRequest(buildRequest(mModel.getBClassList()), 2);
    }

    public void getErrorStore(){
        doRequest(buildRequest((mModel.getStoreMsg())),3);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            mRootView.addSuccess();
        } else if (what == 2) {
            mRootView.getType(((List<BClass>) result.data));
        } else if (what==3){
            mRootView.getStoreErrorMsg(((ErrorBusiness) result.data));
        }
    }
}
