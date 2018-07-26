package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.Address;


public interface AddAddressContract {

    interface View extends IView {
        void showAddress(Address address);
    }
}
