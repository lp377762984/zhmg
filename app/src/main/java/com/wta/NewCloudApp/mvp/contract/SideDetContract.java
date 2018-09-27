package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.wta.NewCloudApp.mvp.model.entity.BusinessNew;


public interface SideDetContract {

    interface View extends IView {

        void showStoreDet(BusinessNew business);
    }

}
