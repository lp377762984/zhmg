package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.jiuwei210278.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean needGuide = AppConfig.getInstance().getBoolean("need_guide", true);
                if (needGuide) ArmsUtils.startActivity(GuideActivity.class);
                else ArmsUtils.startActivity(MainActivity.class);
                finish();
            }
        }, 3000);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();//禁用返回键
    }
}
