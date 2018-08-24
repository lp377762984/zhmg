package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.mvp.view.UIMode;
import com.wta.NewCloudApp.uitls.DialogUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class BaseLoadingActivity<P extends IPresenter> extends BaseActivity<P> implements IView, UIMode {
    private Dialog progress;
    protected static final int UIMODE_NORMAL = 0;//状态栏着色，占位置(默认)
    protected static final int UIMODE_FULLSCREEN_NOTALL = 1;//全屏,虚拟键透明
    protected static final int UIMODE_FULLSCREEN = 2;//全屏(状态栏透明，虚拟键透明)
    protected static final int UIMODE_TRANSPARENT = 3;//状态栏透明，且不占位置 全透明
    protected static final int UIMODE_TRANSPARENT_NOTALL = 4;//状态栏透明，且不占位置 半透明
    protected static final int UIMODE_TRANSPARENT_FULLSCREEN = 5;//全屏，状态栏半透明，虚拟键半透明，状态栏有文字

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setUITheme();
        super.onCreate(savedInstanceState);
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
    public void showLoading() {
        if (progress == null) {
            progress = DialogUtils.createWaitDialog(this);
            progress.setCanceledOnTouchOutside(false);
            progress.setCancelable(false);
        }
        progress.show();
    }

    @Override
    public void hideLoading() {
        if (progress != null && progress.isShowing()) progress.dismiss();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    public void showToast(String content) {
        ArmsUtils.makeText(App.getInstance(), content);
    }

    @Override
    public int setUIMode() {
        return 0;
    }

    private void setUITheme() {
        Window window = getWindow();
        int uiMode = setUIMode();
        if (uiMode == UIMODE_FULLSCREEN) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
        } else if (uiMode == UIMODE_FULLSCREEN_NOTALL) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else if (uiMode == UIMODE_TRANSPARENT || uiMode == UIMODE_TRANSPARENT_NOTALL || uiMode == UIMODE_TRANSPARENT_FULLSCREEN) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                if (uiMode == UIMODE_TRANSPARENT_FULLSCREEN) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (uiMode == UIMODE_TRANSPARENT_NOTALL) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//全透明或半透明的关键(此处为半透明)
                } else if (uiMode == UIMODE_TRANSPARENT_FULLSCREEN) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//全透明或半透明的关键(此处为半透明)
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                }
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }

    @Override
    public boolean useFragment() {
        return false;
    }

    @Override
    public boolean useEventBus() {
        return false;
    }
}
