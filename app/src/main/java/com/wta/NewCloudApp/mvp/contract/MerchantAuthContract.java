package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.AuthInfo;


public interface MerchantAuthContract {

    interface View extends IView {

        void uploadSuccess(AuthInfo data);
    }

}
