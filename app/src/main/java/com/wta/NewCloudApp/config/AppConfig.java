package com.wta.NewCloudApp.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.wta.NewCloudApp.mvp.model.entity.User;

/**
 * Created by 李平 on 2017/8/11.
 * SharedPreferences管理
 */

public class AppConfig {
    private static AppConfig appConfig;

    private SharedPreferences preferences;


    private AppConfig() {
        preferences = App.getInstance().getSharedPreferences("app_config", Context.MODE_PRIVATE);
    }

    public static AppConfig getInstance() {
        if (appConfig == null)
            synchronized (AppConfig.class) {
                if (appConfig == null)
                    appConfig = new AppConfig();
            }
        return appConfig;
    }

    public void putInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public void putString(String key, String value) {
        if (value != null && !value.equals(getString(key, null))) {
            preferences.edit().putString(key, value).apply();
        }
    }

    public String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public void putBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public void putLong(String key, long value) {
        preferences.edit().putLong(key, value).apply();
    }

    public long getLong(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }

    public void clearXml() {
        preferences.edit().clear().apply();
    }
    public void removeValue(String key){
        preferences.edit().remove(key).apply();
    }
    public void clearUser(){
        preferences.edit().remove("avatar")
                .remove("white_score")
                .remove("nickname")
                .remove("card_status")
                .remove("number")
                .remove("mobile")
                .remove("is_weixin")
                .remove("is_mobile")
                .remove("wx_name")
                .remove("group_name")
                .apply();
    }

    public void putUser(User user){
        AppConfig.getInstance().putString("avatar", user.avatar);
        AppConfig.getInstance().putInt("white_score", user.white_score);
        AppConfig.getInstance().putString("nickname", user.nickname);
        AppConfig.getInstance().putInt("card_status", user.card_status);
        AppConfig.getInstance().putString("number", user.number);
        AppConfig.getInstance().putString("mobile", user.mobile);
        AppConfig.getInstance().putInt("is_weixin", user.is_weixin);
        AppConfig.getInstance().putInt("is_mobile", user.is_mobile);
        AppConfig.getInstance().putString("wx_name", user.wx_name);
        AppConfig.getInstance().putString("group_name", user.group_name);
    }
}
