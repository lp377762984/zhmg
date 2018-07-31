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

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;

import com.jess.arms.base.BaseActivity;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.umeng.analytics.MobclickAgent;
import com.wta.NewCloudApp.mvp.ui.activity.MainActivity;

import timber.log.Timber;

/**
 * ================================================
 * 展示 {@link Application.ActivityLifecycleCallbacks} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {
    private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {
        String SYSTEM_REASON = "reason";
        String SYSTEM_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (SophixConfig.patchStatusCode == PatchStatus.CODE_LOAD_RELAUNCH) {
                String action = intent.getAction();
                if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                    String reason = intent.getStringExtra(SYSTEM_REASON);
                    if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
                        killProcess(context, true);
                    }
                } else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
                    killProcess(context, true);
                }
            }
        }
    };

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //Timber.w("%s - onActivityCreated", activity);
        //加入锁屏和home键监听
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        activity.registerReceiver(mHomeKeyEventReceiver, intentFilter);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        //Timber.w("%s - onActivityStarted", activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        //Timber.w("%s - onActivityResumed", activity);
        if (activity instanceof BaseActivity) MobclickAgent.onResume(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        //Timber.w("%s - onActivityPaused", activity);
        if (activity instanceof BaseActivity) MobclickAgent.onResume(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        //Timber.w("%s - onActivityStopped", activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        //Timber.w("%s - onActivitySaveInstanceState", activity);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        //Timber.w("%s - onActivityDestroyed", activity);
        //横竖屏切换或配置改变时, Activity 会被重新创建实例, 但 Bundle 中的基础数据会被保存下来,移除该数据是为了保证重新创建的实例可以正常工作
        activity.getIntent().removeExtra("isInitToolbar");
        activity.unregisterReceiver(mHomeKeyEventReceiver);
        if (activity instanceof MainActivity) {
            if (SophixConfig.patchStatusCode == PatchStatus.CODE_LOAD_RELAUNCH) {
                killProcess(activity,false);
            }
        }
    }

    private void killProcess(Context context, boolean needReboot) {
        SophixManager.getInstance().killProcessSafely();
        if (needReboot) {
            Activity activity = (Activity) context;
            Intent i = activity.getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(activity.getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
        }
    }

}
