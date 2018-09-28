package com.wta.NewCloudApp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.Serializable;

public class PictureC implements Serializable,Cloneable {
    @SerializedName("image")
    public String url;
    @SerializedName("title")
    public String desc="";
    public File file;

    public PictureC() {
    }

    public PictureC(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "{" +
                "image='" + url + '\'' +
                ", title='" + desc + '\'' +
                '}';
    }

    @Override
    public PictureC clone() {
        try {
            return (PictureC) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
