package com.wta.NewCloudApp.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IView;


public interface SideContract {
    interface View extends IView {
        Activity getFragmentContext();
    }
}
