package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;


public interface AddressListContract {

    interface View extends IView {
        void setDefault();
        void deleteSuccess();
    }
}
