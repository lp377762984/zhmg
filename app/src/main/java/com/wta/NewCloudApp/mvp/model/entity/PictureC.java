package com.wta.NewCloudApp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class PictureC {
    @SerializedName("image")
    public String url;
    @SerializedName("title")
    public String desc;
    public File file;
}
