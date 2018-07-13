package com.wta.NewCloudApp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

public class Bill {
    @SerializedName(value = "money",alternate = "score")
    private String money;
    private String remark;
    private String type;
    private String time;
    private String title;
    private String icon;
    private String billType;
    private String billMore;


    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillMore() {
        return billMore;
    }

    public void setBillMore(String billMore) {
        this.billMore = billMore;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
