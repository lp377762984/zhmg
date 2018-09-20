package com.wta.NewCloudApp.mvp.model.entity;

public class Bill {
    /**
     * bill_id : 5
     * score : 99
     */
    public String avatar;
    public String mobile;
    public int bill_id;
    public String score;
    public String money;
    public String remark;
    public String type;
    public String time;
    public String title;
    public String icon;
    public String billType;
    public String billMore;
    /**
     * platform_money : ¥ 0.76
     * business_money : - 0.24
     * business_white_score : + 1000
     * ordersn : 201808281011155199985b84af43cb65
     * pay_type : 微信
     */

    public String platform_money;
    public String business_money;
    public String business_white_score;
    public String ordersn;
    public String pay_type;
    public String status;
    /**
     * shop_name : 饭饭
     * level_name : 8级店铺
     */

    public String shop_name;
    public String level_name;
    public String recommend_score;
    /**
     * totalType : moneyProfit
     * maxTitle : 现金收益
     * minTtile : 收款
     * createTime : 2018-09-05 09:02:50
     */

    public String totalType;
    public String maxTitle;
    public String minTtile;
    public String createTime;
    public String create_time;


    public String getMoney() {
        return money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Bill(String money, String remark, String type, String time, String title, String icon, String billType, String billMore) {
        this.money = money;
        this.remark = remark;
        this.type = type;
        this.time = time;
        this.title = title;
        this.icon = icon;
        this.billType = billType;
        this.billMore = billMore;
    }


}
