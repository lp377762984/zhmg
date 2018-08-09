package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.AuthInfo;
import com.wta.NewCloudApp.mvp.model.entity.ErrorBusiness;


public interface MerchantAuthContract {

    interface View extends IView {

        void uploadSuccess(AuthInfo data);
        void getStoreErrorMsg(ErrorBusiness errorBusiness);
    }

}
