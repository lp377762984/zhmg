package com.wta.NewCloudApp.mvp.model.entity;

import com.flyco.tablayout.listener.CustomTabEntity;

public class DetTabEntity implements CustomTabEntity {
    @Override
    public String getTabTitle() {
        return null;
    }

    @Override
    public int getTabSelectedIcon() {
        return 0;
    }

    @Override
    public int getTabUnselectedIcon() {
        return 0;
    }
}
