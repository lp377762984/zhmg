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

import android.app.Application;
import android.content.Context;

import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.utils.ArmsUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.wta.NewCloudApp.jiuwei210278.BuildConfig;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * ================================================
 * 展示 {@link AppLifecycles} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class AppLifecyclesImpl implements AppLifecycles {

    @Override
    public void attachBaseContext(Context base) {
        //MultiDex.install(base);
    }

    @Override
    public void onCreate(Application application) {
        //Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(application));
        if (LeakCanary.isInAnalyzerProcess(application)) {
            return;
        }
        if (BuildConfig.LOG_DEBUG) {//Timber初始化
            Timber.plant(new Timber.DebugTree());
            ButterKnife.setDebug(true);
        }
        ArmsUtils.obtainAppComponentFromContext(application).extras()
                .put(RefWatcher.class.getName(), BuildConfig.USE_CANARY ? LeakCanary.install(application) : RefWatcher.DISABLED);
        //umeng
        UMConfigure.init(application,"5b1ddaf8f29d9850ae0000e1","umeng", UMConfigure.DEVICE_TYPE_PHONE,"");
        PlatformConfig.setWeixin("wx2a1f186b101a5e06","fa8346fdf7923eebb9d82ad5d1329d58");
    }

    @Override
    public void onTerminate(Application application) {

    }
}
