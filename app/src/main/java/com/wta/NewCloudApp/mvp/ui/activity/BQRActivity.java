package com.wta.NewCloudApp.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.di.component.DaggerBQRComponent;
import com.wta.NewCloudApp.di.module.BQRModule;
import com.wta.NewCloudApp.mvp.contract.BQRContract;
import com.wta.NewCloudApp.mvp.presenter.BQRPresenter;
import com.wta.NewCloudApp.mvp.ui.widget.MoneyBar;
import com.wta.NewCloudApp.uitls.BitmapUtils;
import com.wta.NewCloudApp.uitls.ConfigTag;
import com.wta.NewCloudApp.uitls.QRCodeEncoder;
import com.wta.NewCloudApp.uitls.ScreenDpiUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;


public class BQRActivity extends BaseLoadingActivity<BQRPresenter> implements BQRContract.View {

    @BindView(R.id.mb)
    MoneyBar mb;
    @BindView(R.id.im_qr)
    ImageView imQr;
    private Bitmap qrCode;
    private BottomSheetDialog btmDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBQRComponent
                .builder()
                .appComponent(appComponent)
                .bQRModule(new BQRModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bqr;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Glide.with(this).load(AppConfig.getInstance().getString(ConfigTag.AVATAR, null)).into(new SimpleTarget<Drawable>() {
            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                setImQr(null);
            }

            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                setImQr(resource);
            }

        });
        mb.setCallBack(mb.new CallbackImp(){
            @Override
            public void clickTail() {
                showBtmDialog();
            }
        });
    }

    private void showBtmDialog() {
        if (btmDialog == null) {
            btmDialog = new BottomSheetDialog(this);
            btmDialog.setContentView(R.layout.seller_qr_dialog);
            btmDialog.findViewById(R.id.tv_record).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("暂未开放");
                }
            });
            btmDialog.findViewById(R.id.tv_use).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToast("暂未开放");
                }
            });
            btmDialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btmDialog.dismiss();
                }
            });
        }
        btmDialog.show();
    }

    private void setImQr(Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable != null) {
            RoundedImageView centerIm = (RoundedImageView) getLayoutInflater().inflate(R.layout.center_im, null);
            centerIm.setImageDrawable(drawable);
            bitmap = BitmapUtils.convertViewToBitmap(centerIm);
        }
        int wah = (int) ScreenDpiUtils.dp2px(BQRActivity.this, 250);
        qrCode = QRCodeEncoder.syncEncodeQRCode(AppConfig.getInstance().getString(ConfigTag.SHARE_URL,null),
                wah, Color.BLACK, bitmap);
        imQr.setImageBitmap(qrCode);
    }

    @OnClick({R.id.tv_record, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_record:
                showToast("暂未开放");
                break;
            case R.id.btn_save:
                saveBitmap();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        qrCode = null;
    }
    @SuppressLint("CheckResult")
    private void saveBitmap() {
        new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            File file = new File(Environment.getExternalStorageDirectory() + "/temp", "zhmg_seller_code_20180627.png");
                            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                            try {
                                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                                qrCode.compress(Bitmap.CompressFormat.PNG, 100, bos);
                                bos.flush();
                                bos.close();
                                ArmsUtils.makeText(App.getInstance(), "已保存到" + file.getAbsolutePath());
                            } catch (IOException e) {
                                e.printStackTrace();
                                ArmsUtils.makeText(App.getInstance(), "保存失败");
                            }
                        }
                        else ArmsUtils.makeText(App.getInstance(), "请打开读写权限");
                    }
                });
    }

}
