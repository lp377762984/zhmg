/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wta.NewCloudApp.config;

import android.content.Context;

import com.jess.arms.http.GlobalHttpHandler;

import com.wta.NewCloudApp.uitls.EncodeUtils;
import com.wta.NewCloudApp.uitls.PackageUtils;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ================================================
 * 展示 {@link GlobalHttpHandler} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class GlobalHttpHandlerImpl implements GlobalHttpHandler {
    private Context context;

    public GlobalHttpHandlerImpl(Context context) {
        this.context = context;
    }

    @Override
    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
        return response;
    }

    @Override
    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
        String nonceStr = EncodeUtils.makeNonceStr();
        String url = request.url().toString();
        String[] strs = EncodeUtils.makeSignHead(nonceStr, url);
        return chain.request().newBuilder()
                .header("apiversion", PackageUtils.getPackageVersion(context))
                .header("clientfrom", "android" + PackageUtils.getAndroidVersion())
                .header("deviceuuid", PackageUtils.getDeviceId())
                .header("noncestr", nonceStr)
                .header("sign",strs[1])
                .header("timestamp", strs[0])
                .header("sessionId", AppConfig.getInstance().getString("session_id", "no_id"))
                .header("accessToken", AppConfig.getInstance().getString("access_token", "no_token"))
                /*.header("network", AppConfig.getInstance().getString("network",null))*/
                .build();
    }
}
