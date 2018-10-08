package com.wta.NewCloudApp.uitls;

import com.wta.NewCloudApp.BuildConfig;
import com.wta.NewCloudApp.config.AppConfig;

public class FinalUtils {
    public static final String SEVER_DEBUG = "http://appdemo.jjzbest.com";
    public static final String SEVER_RELEASE = "http://zhmg.jjzbest.com";
    public static String SERVER_URL = BuildConfig.DEBUG ? AppConfig.getInstance().getString(ConfigTag.SERVER_URL, SEVER_DEBUG) : SEVER_RELEASE;
    public static final int REQUEST_SET_NAME = 2018;
    public static final int REQUEST_AUTH = 2019;
    public static final int REQUEST_BIND = 2020;
    public static final int REQUEST_REC_CODE = 2021;
    public static final int REQUEST_ADD_CARD = 2022;
    public static final String REGISTER_PROTOCOL = SERVER_URL + "/htmlProtocol";

    public static final String ABOUT_US = SERVER_URL + "/about";
    public static final String BUSINESS_PROTOCOL = SERVER_URL + "/htmlAdmission";
    public static final String WX_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

    public static final int REQUEST_ADDRESS = 2023;
    public static final int REQUEST_BAUTH = 2024;
    public static final int REQUEST_TAG = 2025;
    public static final int REQUEST_BINFO = 2026;
    public static final int REQUEST_PHONE = 2027;
    public static final int REQUEST_DESC = 2028;
    public static final String WX_APP_ID = "wx2a1f186b101a5e06";
    public static final String WX_APP_SECRET = "fa8346fdf7923eebb9d82ad5d1329d58";
    public static final String WX_USER_INFO = "https://api.weixin.qq.com/sns/userinfo";
    public static final String SELLER_QR_DESC = SERVER_URL + "/receiptCode";
    public static final String SCORE_DESC = SERVER_URL + "/integralExplain ";
    public static final String HOME_DESC = SERVER_URL + "/IntegralStore";
    public static final int REQUEST_PIC_DET = 2029;
    public static final int REQUEST_LOC = 2030;
    public static final int REQUEST_LOC_DET = 2031;
    public static final String SHARE_EXPLAIN = SERVER_URL + "/shareExplain";
}
