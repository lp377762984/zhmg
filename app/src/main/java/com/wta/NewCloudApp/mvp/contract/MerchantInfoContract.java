package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.BClass;
import com.wta.NewCloudApp.mvp.model.entity.BType;
import com.wta.NewCloudApp.mvp.model.entity.ErrorBusiness;

import java.util.List;


public interface MerchantInfoContract {

    interface View extends IView {
        void addSuccess();

        void getType(List<BClass> classes);

        void getStoreErrorMsg(ErrorBusiness errorBusiness);
    }
}
