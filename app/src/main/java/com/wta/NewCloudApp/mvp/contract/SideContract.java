package com.wta.NewCloudApp.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IView;
import com.wta.NewCloudApp.manager.LocationManager;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.view.BaseDataView;


public interface SideContract {
    interface View extends BaseDataView {
        Activity getFragmentContext();

        void handleBState(Result<Business> businessResult);
        void destroyLocation(LocationManager manager);
    }
}
