package com.wta.NewCloudApp.mvp.presenter;

import com.amap.api.location.AMapLocation;
import com.jess.arms.di.scope.FragmentScope;
import com.wta.NewCloudApp.manager.LocationManager;
import com.wta.NewCloudApp.mvp.contract.SideContract;
import com.wta.NewCloudApp.mvp.model.BusinessModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import java.util.List;

import javax.inject.Inject;


@FragmentScope
public class SidePresenter extends BBasePresenter<BusinessModel, SideContract.View> {
    private LocationManager locationManager;
    private double lat;
    private double lng;

    @Inject
    public SidePresenter(BusinessModel model, SideContract.View rootView) {
        super(model, rootView);
    }

    public void getData(boolean isRefresh) {
        if (isRefresh) {
            if (lat == 0) {
                startLocation();
            } else {
                doRequest(buildListRequest(mModel.getBusiness(isRefresh, lat, lng)), 1);
            }

        } else
            doRequest(buildListRequest(mModel.getBusiness(isRefresh, lat, lng)), 1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            mRootView.getData(what, (List) result.data);
        }
    }

    private void startLocation() {
        mRootView.showLoading();
        if (locationManager == null) {
            locationManager = new LocationManager(mRootView.getFragmentContext(), new LocationManager.LocateListener() {
                @Override
                public void onLocateSuccess(AMapLocation location) {
                    mRootView.hideLoading();
                    if (lat == 0) {
                        doRequest(buildListRequest(mModel.getBusiness(true, location.getLatitude(), location.getLongitude())), 1);
                    }
                    SidePresenter.this.lat = location.getLatitude();
                    SidePresenter.this.lng = location.getLongitude();
                }

                @Override
                public boolean onLocateFailed(AMapLocation location) {
                    mRootView.hideLoading();
                    return false;
                }
            });
        }
        locationManager.start();
    }

}
