package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.Business;


public interface SweepContract {

    interface View extends IView {

        void showBusinessMsg(Business business);
    }
}
