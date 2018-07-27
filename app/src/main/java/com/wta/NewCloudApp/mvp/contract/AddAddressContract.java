package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.wta.NewCloudApp.mvp.model.entity.Address;
import com.wta.NewCloudApp.mvp.model.entity.Province;

import java.util.ArrayList;


public interface AddAddressContract {

    interface View extends IView {
        void showAddress(Address address);
        void initAddressSuccess(ArrayList<Province> provinces);
    }
}
