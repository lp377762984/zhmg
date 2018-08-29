package com.wta.NewCloudApp.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

public class PayInfo {

    /**
     * appid : wx2a1f186b101a5e06
     * partnerid : 1504818861
     * prepayid : 5b767f15b4f93
     * package : Sign=WXPay
     * noncestr : 5b767f15b4ffe
     * timestamp : 1534492437
     * sign : 75D875142C4481FAAA04451AD7444340
     */

    public String appid;
    public String partnerid;
    public String prepayid;
    @SerializedName("package")
    public String packageX;
    public String noncestr;
    public int timestamp;
    public String sign;
    public String info;
    public String out_trade_no;
}
