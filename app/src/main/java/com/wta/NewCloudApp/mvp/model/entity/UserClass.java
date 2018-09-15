package com.wta.NewCloudApp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

public class UserClass {
    @SerializedName("avatar")
    public String classLogo;
    @SerializedName("title")
    public String clazz;
    public String money;
    public String desc;
    public String bg;
    public int grade_id;
    public String years;
    public String status;
}
