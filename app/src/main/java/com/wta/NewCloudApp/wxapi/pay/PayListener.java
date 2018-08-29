package com.wta.NewCloudApp.wxapi.pay;

public interface PayListener {
    /**
     * 支付完成
     *
     * @param payType   支付方式 1：Alipay；2：weixin
     * @param errorCode 错误码（以微信为准）0成功 -1错误 -2取消 -3网络错误 -4其他错误
     */
    void payComplete(int payType, int errorCode);
}
