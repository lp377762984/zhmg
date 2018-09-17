package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.VIPInfo;


public interface UpdateClazzContract {

    interface View extends IView {

        void showVIPInfo(VIPInfo vipInfo);
    }

}
