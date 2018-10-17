package com.wta.NewCloudApp.uitls;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.amap.api.location.CoordinateConverter;

import timber.log.Timber;

public class IntentUtils {
    private static final String BAIDU_MAP_NAVI_URI = "baidumap://map/marker?";
    private static final String GAODE_MAP_NAVI_URI = "androidamap://viewMap?sourceApplication=lefaner";
    private static final String QQ_MAP_NAVI_URI = "qqmap://map/marker?";

    public static final String BAIDU_MAP_APP = "com.baidu.BaiduMap";
    public static final String GAODE_MAP_APP = "com.autonavi.minimap";
    public static final String TX_MAP_APP = "com.tencent.map";

    public static void goNaviByGaoDeMap(Activity activity, String lat, String lon, String name) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uri = Uri.parse(GAODE_MAP_NAVI_URI +
                "&poiname=" + name +
                "&lat=" + lat +
                "&lon=" + lon +
                "&dev=0");
        intent.setData(uri);
        intent.setPackage(GAODE_MAP_APP);
        activity.startActivity(intent);
    }

    public static void baiduMarker(Activity activity, String lat, String lon, String name) {
        Intent intent = new Intent();
        //GCJ-02=>BD09 火星坐标系=>百度坐标系
        double x = Double.parseDouble(lon);
        double y = Double.parseDouble(lat);
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * Math.PI * 3000.0 / 180.0);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(y * Math.PI * 3000.0 / 180.0);
        lat = (z * Math.sin(theta) + 0.006) + "";
        lon = (z * Math.cos(theta) + 0.0065) + "";
        intent.setData(Uri.parse(BAIDU_MAP_NAVI_URI + "location="
                + lat + "," +
                lon + "&title=" +
                name + "&src=" +
                "android.com.lefaner"
        ));

        activity.startActivity(intent);
    }

    public static void txMarker(Activity activity, String lat, String lon, String name) {
        Uri uri = Uri.parse(QQ_MAP_NAVI_URI + "marker=coord:" +
                lat + "," +
                lon + ";title:" +
                name + "&referer=" + "66VBZ-2BNRF-QIGJE-JIE26-TX3QS-SSB6U"
        );
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(intent);
    }

    public static void installMap(Activity activity) {
        Intent intent;
        Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
        intent = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(intent);
    }

    public static void restartApp(Activity activity) {
        Intent i = activity.getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(activity.getBaseContext().getPackageName());
        if (i != null) {
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(i);
        }
    }

}
