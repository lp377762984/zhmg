package com.wta.NewCloudApp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

public class Result<T> {
    public int code;
    public String msg;
    public T data;
    public String white_score;
    @SerializedName("version_upgrade")
    public AppUpdate update;
    /**
     * sumNumber : {"sumProfit":2000,"receivablesNumber":7,"integralIncome":13}
     */

    public SumNumberBean sumNumber;

    public static class AppUpdate{
        public int android_type;
        public int ios_type;
    }

    public Result(int code) {
        this.code = code;
    }

    public Result() {
    }
    public LoginAccessBean login_access;
    public static class LoginAccessBean {
        public String sessionId;
    }

    public static class SumNumberBean {
        /**
         * sumProfit : 2000
         * receivablesNumber : 7
         * integralIncome : 13
         */

        public String sumProfit;
        public int receivablesNumber;
        public String integralIncome;
    }
}
