package com.wta.NewCloudApp.mvp.presenter;

import com.amap.api.location.AMapLocation;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.ArmsUtils;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.manager.LocationManager;
import com.wta.NewCloudApp.mvp.contract.SideContract;
import com.wta.NewCloudApp.mvp.model.BusinessModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;
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
        if (isRefresh) startLocation();
        else
        doRequest(buildListRequest(mModel.getBusiness(isRefresh, lat, lng)), 1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            if (mRootView instanceof BaseDataView) {
                ((BaseDataView) mRootView).getData(what, (List) result.data);
            } else {
                ArmsUtils.makeText(App.getInstance(), "view is not implements baseDataView");
            }
        }
    }

    private void startLocation() {
        mRootView.showLoading();
        if (locationManager == null) {
            locationManager = new LocationManager(mRootView.getFragmentContext(), new LocationManager.LocateListener() {
                @Override
                public void onLocateSuccess(AMapLocation location) {
                    mRootView.hideLoading();
                    SidePresenter.this.lat = location.getLatitude();
                    SidePresenter.this.lng = location.getLongitude();
                    doRequest(buildListRequest(mModel.getBusiness(true, lat, lng)), 1);
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
