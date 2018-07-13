package com.wta.NewCloudApp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

public class Result<T> {
    public int code;
    public String msg;
    public T data;
    @SerializedName("version_upgrade")
    public AppUpdate update;
    public static class AppUpdate{
        public int android_type;
        public int ios_type;
    }

    public Result(int code) {
        this.code = code;
    }

    public Result() {
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", update=" + update +
                '}';
    }
}
