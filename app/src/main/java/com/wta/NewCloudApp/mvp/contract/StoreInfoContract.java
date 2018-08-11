package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.Business;


public interface StoreInfoContract {

    interface View extends IView {

        void showStoreInfo(Business data);

        void saveSuccess(Business data);
    }

}
