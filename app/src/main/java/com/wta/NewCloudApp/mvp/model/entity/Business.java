package com.wta.NewCloudApp.mvp.model.entity;

public class Business {
    public String icon;
    public String name;
    public String type;
    public String scoreType;
    public String time;
    public String location;
    public String distance;

    public Business(String icon, String name, String type, String scoreType, String time, String location, String distance) {
        this.icon = icon;
        this.name = name;
        this.type = type;
        this.scoreType = scoreType;
        this.time = time;
        this.location = location;
        this.distance = distance;
    }
}
