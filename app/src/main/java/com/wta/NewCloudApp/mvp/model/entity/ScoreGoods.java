package com.wta.NewCloudApp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 积分商品
 */
public class ScoreGoods {

    /**
     * goods_id : 29
     * integral : 100
     * title : 商品名称
     * avatar : http://zhmg.com/public/uploads/product/2018-10-15/5bc4003548f09.jpg
     */

    public int goods_id;
    public String integral;
    public String title;
    public String avatar;


    /**
     * order_id : 2
     * goods_img : http://zhmg.com/public/uploads/product/2018-10-15/5bc4003548f09.jpg
     * goods_name : 商品名称
     * num : 1
     * integral : 100
     * unit_integral : 100
     * status : 0
     * type : 2
     * store_id : 2
     * store_name : 中国人民
     */

    public int order_id;
    public String goods_img;
    public String goods_name;
    public int num;
    public String unit_integral;
    public int status;
    public int type;
    public int store_id;
    public String store_name;

    /**
     * integral : 100
     * type_id : 1
     * shop_name : 中国人民
     * address : 北京市北京市丰台区新村街道办事处
     * address_details :  地下3楼
     * location_address : 北京市丰台区五圈南路
     * distance : 0km
     * type_name : 海鲜
     */

    public int type_id;
    public String shop_name;
    public String address;
    public String address_details;
    public String location_address;
    public String distance;
    public String type_name;
    public String store_img;
    public String sign_img;
}
