package com.wta.NewCloudApp.uitls;

import android.content.Context;
import android.location.LocationManager;

public class SystemUtils {
    /**
     * 手机是否开启位置服务，如果没有开启那么所有app将不能使用定位功能
     */
    public static boolean isLocServiceEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = false;
        boolean network = false;
        if (locationManager != null) {
            gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }
        return gps || network;
    }
}
