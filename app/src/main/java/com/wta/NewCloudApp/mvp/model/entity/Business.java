package com.wta.NewCloudApp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

public class Business {
    public String icon;
    public String name;
    public String type;
    public String scoreType;
    public String time;
    public String location;
    public String distance;
    public int code_type;
    @SerializedName("/shopDetails")
    public String _$ShopDetails138; // FIXME check this code
    public String debug;
    public String shop_name;
    public String shop_type;
    public String shop_level;
    public String shop_address_x;
    public String shop_address_y;
    public String start_time;
    public String end_time;
    public String shop_doorhead;
    public String province;
    public String city;
    public String district;
    public String twon;
    public String location_address;
    public String address;
    public int create_time;

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
