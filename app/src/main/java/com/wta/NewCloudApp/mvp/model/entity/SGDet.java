package com.wta.NewCloudApp.mvp.model.entity;

import java.util.List;

/**
 * 礼品详情
 */
public class SGDet {

    /**
     * goods_id : 11
     * title : 石榴
     * stock : 99
     * considerations : 1
     * photos : ["http://appdemo.jjzbest.com/public/uploads/goods/2018-10-23/5bcec3b7ae195.png"]
     * integral : 100
     * contents : http://appdemo.jjzbest.com/shopDetails/id/11
     * receiving_address : null
     * express : 包邮
     */

    public int goods_id;
    public String title;
    public int stock;
    public String considerations;
    public String integral;
    public String contents;
    public Address receiving_address;
    public String express;
    public List<String> photos;

    /**
     * shop_id : 2
     * shop_name : 中国人民
     * shop_doorhead : http://appdemo.jjzbest.com/public/uploads/condition/4/20180927113604_18484.jpeg
     */

    public int shop_id;
    public String shop_name;
    public String shop_doorhead;

    /**
     * store_name : 中国人民
     * store_id : 2
     * store_address : 北京市丰台区五圈南路 地下3楼
     * goods_img : http://zhmg.com/public/uploads/product/2018-10-15/5bc4003548f09.jpg
     * goods_name : 商品名称
     * unit_integral : 100
     * num : 1
     * integral : 100
     * ordersn : 6d84d77c153977
     * create_time : 2018-10-17 17:54:32
     * status : 待取货
     */

    public String store_name;
    public int store_id;
    public String store_address;
    public String goods_img;
    public String goods_name;
    public String unit_integral;
    public int num;
    public String ordersn;
    public String create_time;
    public int status;

    /**
     * consignee : hjskad
     * mobile : 18765623434
     * address : 北京市丰台区
     */

    public String consignee;
    public String mobile;
    public String address;
    public String update_time;
    public int order_id;
}
