package com.wta.NewCloudApp.config;

import android.net.ParseException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.jess.arms.utils.ArmsUtils;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import org.devio.takephoto.model.TImage;
import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import retrofit2.HttpException;
import timber.log.Timber;

public class DefaultHandleSubscriber<K> extends ErrorHandleSubscriber<K> {
    private HttpResponseHandler handler;
    private int what = -2018;

    public DefaultHandleSubscriber(RxErrorHandler rxErrorHandler) {
        super(rxErrorHandler);
    }

    public DefaultHandleSubscriber(RxErrorHandler rxErrorHandler, int what, HttpResponseHandler handler) {
        super(rxErrorHandler);
        this.what = what;
        this.handler = handler;
    }

    public int getWhat() {
        return what;
    }

    public DefaultHandleSubscriber<K> setWhat(int what) {
        this.what = what;
        return this;
    }

    public HttpResponseHandler getHandler() {
        return handler;
    }

    public DefaultHandleSubscriber<K> setHandler(HttpResponseHandler handler) {
        this.handler = handler;
        return this;
    }

    @Override
    public void onNext(K k) {
        if (k instanceof Result) {
            Result rt = (Result) k;
            switch (rt.code) {
                case 10:
                    if (handler != null) handler.handle10(what, rt);
                    break;
                case 11:
                    if (handler != null) handler.handle11(what, rt);
                    break;
                case 20:
                    if (handler != null) handler.handle20(what, rt);
                    break;
                case 200:
                    if (handler != null) handler.handle200(what, rt);
                    break;
                case 404:
                    if (handler != null) handler.handle404(what, rt);
                    break;
                default:
                    ArmsUtils.snackbarText("没有和客户端约定的响应码");
                    break;
            }
        } else {
            if (handler != null) handler.handleOther(what, k);
        }
    }

    @Override
    public void onError(Throwable t) {
        //super.onError(t);
        Timber.i("onError: "+t.toString());
        String msg;
        if (t instanceof UnknownHostException) {
            msg = "网络不可用";
        } else if (t instanceof SocketTimeoutException) {
            msg = "请求网络超时";
        } else if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            msg = convertStatusCode(httpException);
        } else if (t instanceof JsonIOException || t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException) {
            msg = "数据解析错误";
        } else {
            msg = t.getMessage();
        }
        ArmsUtils.snackbarText(msg);
        if (handler != null) handler.handleException(what, t);
    }

    private String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}
