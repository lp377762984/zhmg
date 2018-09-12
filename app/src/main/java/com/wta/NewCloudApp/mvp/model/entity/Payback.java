package com.wta.NewCloudApp.mvp.model.entity;

import java.io.Serializable;

public class Payback implements Serializable {

    /**
     * msg : 支付成功
     * status : 1
     * money : 1
     * white_score : 1000
     * store_name : 你是
     */

    public String msg;
    public int status;
    public String money;
    public String white_score;
    public String store_name;
}
