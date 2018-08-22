package com.wta.NewCloudApp.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.zxing.Result;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerPayComponent;
import com.wta.NewCloudApp.di.component.DaggerSweepComponent;
import com.wta.NewCloudApp.di.module.PayModule;
import com.wta.NewCloudApp.di.module.SweepModule;
import com.wta.NewCloudApp.mvp.contract.PayContract;
import com.wta.NewCloudApp.mvp.contract.SweepContract;
import com.wta.NewCloudApp.mvp.presenter.SweepPresenter;
import com.wta.NewCloudApp.mvp.ui.widget.qr.camera.CameraManager;
import com.wta.NewCloudApp.mvp.ui.widget.qr.decode.CaptureActivityHandler;
import com.wta.NewCloudApp.mvp.ui.widget.qr.decode.InactivityTimer;
import com.wta.NewCloudApp.mvp.ui.widget.qr.view.QrCodeFinderView;

import java.io.IOException;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import timber.log.Timber;


/**
 * Created by 李平 on 2017/10/12 14:59
 * Describe: 扫一扫界面
 */

public class SweepActivity extends BaseLoadingActivity<SweepPresenter> implements SurfaceHolder.Callback, View.OnClickListener, SweepContract.View {

    @BindView(R.id.rb_switch_light)
    CheckBox switchLight;
    private CaptureActivityHandler mCaptureActivityHandler;
    private boolean mHasSurface;
    private InactivityTimer mInactivityTimer;
    @BindView(R.id.qr_code_view_finder)
    QrCodeFinderView mQrCodeFinderView;
    @BindView(R.id.qr_code_view_stub)
    SurfaceView mSurfaceView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSweepComponent
                .builder()
                .appComponent(appComponent)
                .sweepModule(new SweepModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sweep;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        switchLight.setOnClickListener(this);
        mHasSurface = false;
        CameraManager.init();
        mInactivityTimer = new InactivityTimer(this);
    }

    @SuppressLint("CheckResult")
    private void requestPermissionInitCamera() {
        new RxPermissions(this).request(Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    initCamera();
                } else {
                    finish();
                }
            }
        });
    }

    /**
     * Handler scan result
     */
    public void handleDecode(Result result) {
        mInactivityTimer.onActivity();
        vibrate();
        String text = result.getText();
        boolean needReSweep = true;
        if (TextUtils.isEmpty(text)) {
            ArmsUtils.makeText(this, getString(R.string.warn_not_business_qr_code));
        } else {
            if (text.contains("?number=")) {
                String[] split = text.split("\\?");
                if (split.length == 2) {
                    String number = split[1];
                    String[] split1 = number.split("=");
                    if (split1.length == 2) {
                        if ("number".equals(split1[0])) {
                            needReSweep = false;
                            PayActivity.startPay(this, split1[1]);
                        } else {
                            ArmsUtils.makeText(this, getString(R.string.warn_not_business_qr_code));
                        }
                    } else {
                        ArmsUtils.makeText(this, getString(R.string.warn_not_business_qr_code));
                    }
                } else {
                    ArmsUtils.makeText(this, getString(R.string.warn_not_business_qr_code));
                }
            } else {
                ArmsUtils.makeText(this, getString(R.string.warn_not_business_qr_code));
            }
        }
        if (needReSweep) restartPreview();
        else finish();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        assert vibrator != null;
        vibrator.vibrate(200);
    }

    private void initCamera() {
        SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
        if (mHasSurface) {
            openCamera(surfaceHolder);
        } else {
            Timber.d("surfaceHolder.addCallback");
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
    }

    private void closeCamera() {
        if (mCaptureActivityHandler != null) {
            try {
                mCaptureActivityHandler.quitSynchronously();
                mCaptureActivityHandler = null;
                mHasSurface = false;
                if (null != mSurfaceView) {
                    mSurfaceView.getHolder().removeCallback(this);
                }
                CameraManager.get().closeDriver();
                Timber.d("closeCamera: ");
            } catch (Exception e) {
                finish();
            }
        }
    }

    private void openCamera(SurfaceHolder surfaceHolder) {
        try {
            if (!CameraManager.get().openDriver(surfaceHolder)) {
                showPermissionDeniedDialog();
                return;
            }
        } catch (IOException e) {
            // 基本不会出现相机不存在的情况
            Toast.makeText(this, "没有找到相机", Toast.LENGTH_SHORT).show();
            finish();
            return;
        } catch (RuntimeException re) {
            re.printStackTrace();
            showPermissionDeniedDialog();
            return;
        }
        mQrCodeFinderView.setVisibility(View.VISIBLE);
        if (mCaptureActivityHandler == null) {
            mCaptureActivityHandler = new CaptureActivityHandler(this);
        }
        Timber.d("openCamera: ");
    }

    private void showPermissionDeniedDialog() {
        mQrCodeFinderView.setVisibility(View.GONE);
    }

    private void turnFlashlightOn() {
        try {
            CameraManager.get().setFlashLight(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void turnFlashLightOff() {
        try {
            CameraManager.get().setFlashLight(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void restartPreview() {
        if (null != mCaptureActivityHandler) {
            try {
                mCaptureActivityHandler.restartPreviewAndDecode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Handler getCaptureActivityHandler() {
        return mCaptureActivityHandler;
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestPermissionInitCamera();
        mSurfaceView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeCamera();
        mSurfaceView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        if (null != mInactivityTimer) {
            mInactivityTimer.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!mHasSurface) {
            mHasSurface = true;
            openCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mHasSurface = false;
    }


    @Override
    public void onClick(View v) {
        if (switchLight.isChecked()) {
            turnFlashlightOn();
        } else {
            turnFlashLightOff();
        }
    }
}
