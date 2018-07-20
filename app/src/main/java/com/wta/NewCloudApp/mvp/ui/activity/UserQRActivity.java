package com.wta.NewCloudApp.mvp.ui.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.di.component.DaggerUserQRComponent;
import com.wta.NewCloudApp.di.module.UserQRModule;
import com.wta.NewCloudApp.jiuwei210278.R;
import com.wta.NewCloudApp.mvp.contract.UserQRContract;
import com.wta.NewCloudApp.mvp.presenter.UserQRPresenter;
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
import io.reactivex.functions.Consumer;


public class UserQRActivity extends BaseLoadingActivity<UserQRPresenter> implements UserQRContract.View, MoneyBar.CallBack, View.OnClickListener {

    @BindView(R.id.mb)
    MoneyBar mb;
    @BindView(R.id.im_head)
    RoundedImageView imHead;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.im_qr)
    ImageView imQr;
    private BottomSheetDialog btmDialog;
    private Bitmap qrCode;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserQRComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .userQRModule(new UserQRModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_user_qr; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mb.setCallBack(this);
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
        GlideArms.with(this).load(AppConfig.getInstance().getString(ConfigTag.AVATAR, null)).placeholder(R.mipmap.user_default).into(imHead);
        tvCode.setText(AppConfig.getInstance().getString(ConfigTag.NUMBER, null));

    }

    private void setImQr(Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable != null) {
            RoundedImageView centerIm = (RoundedImageView) getLayoutInflater().inflate(R.layout.center_im, null);
            centerIm.setImageDrawable(drawable);
            bitmap = BitmapUtils.convertViewToBitmap(centerIm);
        }
        int wah = (int) ScreenDpiUtils.dp2px(UserQRActivity.this, 250);
        qrCode = QRCodeEncoder.syncEncodeQRCode(AppConfig.getInstance().getString(ConfigTag.SHARE_URL,null),
                wah, Color.BLACK, bitmap);
        imQr.setImageBitmap(qrCode);
    }

    @Override
    public void clickBack(View back) {
        finish();
    }

    @Override
    public void clickTail() {
        if (btmDialog == null) {
            btmDialog = new BottomSheetDialog(this);
            btmDialog.setContentView(R.layout.user_qr_lat);
            btmDialog.findViewById(R.id.tv_save).setOnClickListener(this);
            btmDialog.findViewById(R.id.tv_cancel).setOnClickListener(this);
        }
        btmDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_save:
                new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) saveBitmap();
                                else ArmsUtils.makeText(App.getInstance(), "请打开读写权限");
                            }
                        });
                if (btmDialog != null && btmDialog.isShowing()) btmDialog.dismiss();
                break;
            case R.id.tv_cancel:
                if (btmDialog != null && btmDialog.isShowing()) btmDialog.dismiss();
                break;
        }
    }

    private void saveBitmap() {
        File file = new File(Environment.getExternalStorageDirectory() + "/temp", "qlqw_seller_code_20180627.png");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        qrCode = null;
    }
}
