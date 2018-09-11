package com.wta.NewCloudApp.mvp.presenter;

import com.amap.api.location.AMapLocation;
import com.jess.arms.di.scope.FragmentScope;
import com.wta.NewCloudApp.manager.LocationManager;
import com.wta.NewCloudApp.mvp.contract.SideContract;
import com.wta.NewCloudApp.mvp.model.BusinessModel;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.ui.fragment.SideFragment;
import com.wta.NewCloudApp.mvp.view.BaseDataView;

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

    public void getBState(){
        doRequest(buildRequest(mModel.getStoreState()),2);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            ((BaseDataView) mRootView).getData(what, (Result<List>) result);
        }else if (what==2){
            mRootView.handleBState((Result<Business>) result);
        }
    }

    @Override
    public void handleException(int what, Throwable t) {
        super.handleException(what, t);
        if (what==1) mRootView.loadFailed();
    }

    private void startLocation() {
        if (locationManager == null) {
            locationManager = new LocationManager(mRootView.getFragmentContext(), new LocationManager.LocateListener() {
                @Override
                public void onLocateSuccess(AMapLocation location) {
                    if (lat == 0) {
                        doRequest(buildListRequest(mModel.getBusiness(true, location.getLatitude(), location.getLongitude())), 1);
                    }
                    SidePresenter.this.lat = location.getLatitude();
                    SidePresenter.this.lng = location.getLongitude();
                }

                @Override
                public boolean onLocateFailed(AMapLocation location) {
                    ((BaseDataView) mRootView).hideListLoading();
                    return false;
                }

                @Override
                public void noPermission() {
                    ((BaseDataView) mRootView).hideListLoading();
                }
            });
        }
        locationManager.start();
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }
}
