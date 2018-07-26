package com.wta.NewCloudApp.mvp.model.entity;

import java.io.Serializable;

public class Address implements Serializable {

    /**
     * address_id : 1
     * consignee : 聂国富
     * address : 汉威国际3区1号楼2M
     * mobile : 15210455141
     * type : 0
     * province : 1
     * city : 710682
     * district : 1106
     * address_region : 北京市 北京市 丰台区
     * address_detail : 北京市 北京市 丰台区 汉威国际3区1号楼2M
     */

    public int address_id;
    public String consignee;
    public String address;
    public String mobile;
    public int type;
    public int province;
    public int city;
    public int district;
    public String address_region;
    public String address_detail;
}
