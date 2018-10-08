package com.wta.NewCloudApp.uitls;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class IntentUtils {
    private static final String BAIDU_MAP_NAVI_URI = "baidumap://map/navi?query=";
    private static final String GAODE_MAP_NAVI_URI = "androidamap://route?sourceApplication=lefaner";

    private static final String BAIDU_MAP_APP = "com.baidu.BaiduMap";
    private static final String GAODE_MAP_APP = "com.autonavi.minimap";
    private static final String TX_MAP_APP = "com.tencent.map";

    private static final String QQ_MAP_URL = "http://apis.map.qq.com/uri/v1/routeplan?type=drive&";

    public static void goNaviByGaoDeMap(Activity activity, String lat, String lon, String dev, String style) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        //将功能Scheme以URI的方式传入data
        Uri uri = Uri.parse(GAODE_MAP_NAVI_URI +
                "Test"
                + "&lat=" + lat
                + "&lon=" + lon
                + "&dev=" + dev
                + "&style=" + style);
        intent.setData(uri);
        intent.setPackage("com.autonavi.minimap");
        activity.startActivity(intent);
    }

    private void goNaviByBaiDuMap(Activity activity, String address) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(BAIDU_MAP_NAVI_URI + address));
        activity.startActivity(intent);
    }

    private void goNaviByTencentMap(Activity activity, String startPoint, String endPoint, String policy, String appName) {
        Uri uri = Uri.parse(QQ_MAP_URL + "from=" + startPoint + "&to=" + endPoint + "&policy=" + policy + "&referer=" + appName);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(intent);
    }

    public static void installMap(Activity activity) {
        Intent intent;
        Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
        intent = new Intent(Intent.ACTION_VIEW, uri);
        activity.startActivity(intent);
    }

    public static void restartApp(Activity activity){
        Intent i = activity.getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(activity.getBaseContext().getPackageName());
        if (i != null) {
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(i);
        }
    }

}
