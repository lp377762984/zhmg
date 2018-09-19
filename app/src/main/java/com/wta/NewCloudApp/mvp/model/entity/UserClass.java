package com.wta.NewCloudApp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserClass implements Serializable{
    @SerializedName("avatar")
    public String classLogo;
    @SerializedName("title")
    public String clazz;
    public String money;
    @SerializedName("introduction")
    public String desc;
    @SerializedName("background_img")
    public String bg;
    public int grade_id;
    public String years;
    public String status;
    @SerializedName("abstract")
    public String _abstract;
    public int open_member;
    public String closing_prompt;
    public String telephone;
    public String time;
}
