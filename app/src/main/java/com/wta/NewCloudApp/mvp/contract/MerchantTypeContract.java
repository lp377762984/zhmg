package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.BType;

import java.util.List;


public interface MerchantTypeContract {

    interface View extends IView {
        void showBTypeList(List<BType> types);
    }
}
