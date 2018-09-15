package com.wta.NewCloudApp.config;


import com.wta.NewCloudApp.mvp.model.entity.Result;

public interface HttpResponseHandler {
    void handle10(int what, Result result);

    void handle11(int what, Result result);

    void handle20(int what, Result result);

    <T> void handle200(int what, Result<T> result);

    <T> void handle404(int what, Result<T> result);

    <K> void handleOther(int what, K k);

    void handleException(int what, Throwable t);

    void handleExcept200(int what, Result rt);
}
