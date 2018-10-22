package com.wta.NewCloudApp.mvp.model.entity;

import java.io.Serializable;

public class BType implements Serializable {
    public String type_name;
    public int type_id;

    public BType(int type_id, String type_name) {
        this.type_id = type_id;
        this.type_name = type_name;
    }
}
