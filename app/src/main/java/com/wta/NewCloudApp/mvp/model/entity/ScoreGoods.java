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
}
