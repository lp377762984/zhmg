package com.wta.NewCloudApp.mvp.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.makeramen.roundedimageview.RoundedImageView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.di.component.DaggerUserQRComponent;
import com.wta.NewCloudApp.di.module.UserQRModule;
import com.wta.NewCloudApp.mvp.contract.UserQRContract;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.Share;
import com.wta.NewCloudApp.mvp.presenter.UserQRPresenter;
import com.wta.NewCloudApp.mvp.ui.widget.MoneyBar;
import com.wta.NewCloudApp.uitls.BitmapUtils;
import com.wta.NewCloudApp.uitls.ConfigTag;
import com.wta.NewCloudApp.uitls.FinalUtils;
import com.wta.NewCloudApp.uitls.QRCodeEncoder;
import com.wta.NewCloudApp.uitls.ScreenDpiUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * 邀请伙伴
 */
public class UserQRActivity extends BaseLoadingActivity<UserQRPresenter> implements UserQRContract.View, MoneyBar.CallBack, View.OnClickListener {

    @BindView(R.id.mb)
    MoneyBar mb;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.im_qr)
    ImageView imQr;
    @BindView(R.id.im)
    ImageView im;
    @BindView(R.id.lat_qr)
    View latQr;
    private Bitmap qrCode;
    private BottomSheetDialog dialog;
    private Share share;
    private int position;//0 分享链接 1 分享二维码
    private File shareFile;

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
        GlideArms.with(this).load(AppConfig.getInstance().getString(ConfigTag.AVATAR, null)).into(new SimpleTarget<Drawable>() {
            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                setImQr(null);
            }

            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                setImQr(resource);
            }

        });
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
        qrCode = QRCodeEncoder.syncEncodeQRCode(AppConfig.getInstance().getString(ConfigTag.SHARE_URL, null),
                wah, Color.BLACK, bitmap);
        imQr.setImageBitmap(qrCode);
    }

    @Override
    public void clickBack(View back) {
        finish();
    }

    @Override
    public void clickTail() {
        ArmsUtils.startActivity(GroupActivity.class);
    }

    private void saveBitmap() {
        File file = new File(Environment.getExternalStorageDirectory() + "/temp", "zhmg_user_code_20180627.png");
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

    @OnClick({R.id.im_info, R.id.btn_link, R.id.btn_big, R.id.im_qr})
    public void clickView(View view) {
        switch (view.getId()) {
            case R.id.im_info:
                WebViewActivity.start(this, "邀请说明", FinalUtils.SHARE_EXPLAIN);
                break;
            case R.id.btn_link:
                position = 0;
                mPresenter.startShare();
                break;
            case R.id.btn_big:
                position = 1;
                mPresenter.shareBigImage();
                break;
            case R.id.im_qr:

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        qrCode = null;
    }

    @Override
    public void share(Result<Share> shareResult) {
        share = shareResult.data;
        if (position == 1) {
            GlideArms.with(this).load(share.share_newImg).error(R.mipmap.share_big).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    Timber.i("onResourceReady: ");
                    addQRToImage(((BitmapDrawable) resource).getBitmap());
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    Timber.i("onLoadFailed: ");
                    addQRToImage(((BitmapDrawable) errorDrawable).getBitmap());
                }

            });
        }
        if (dialog == null) {
            dialog = new BottomSheetDialog(this);
            dialog.setContentView(R.layout.share_dialog);
            dialog.findViewById(R.id.tv_wx).setOnClickListener(this);
            dialog.findViewById(R.id.tv_wx_friends).setOnClickListener(this);
            dialog.findViewById(R.id.tv_qq).setOnClickListener(this);
            dialog.findViewById(R.id.tv_qq_zone).setOnClickListener(this);
            dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }


    @Override
    public void onClick(View v) {
        UMWeb web = null;
        UMImage umImage = null;
        if (position == 0) {
            web = new UMWeb(share.share_url);
            web.setTitle(share.share_title);//标题
            web.setThumb(new UMImage(this, share.share_img));  //缩略图
            web.setDescription(share.share_desc);//描述
        } else {
            if (shareFile == null) {
                showToast("图片正在生成，请稍后");
            } else {
                umImage = new UMImage(this, shareFile);
            }
        }
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
        switch (v.getId()) {
            case R.id.tv_wx:
                if (!UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.WEIXIN)) {
                    ArmsUtils.makeText(App.getInstance(), "您没有安装微信");
                    return;
                }
                ShareAction shareAction1 = new ShareAction(this).setPlatform(SHARE_MEDIA.WEIXIN)
                        .setCallback(new ShareListener());
                if (position == 0) shareAction1.withMedia(web).share();
                else shareAction1.withMedia(umImage).share();
                break;
            case R.id.tv_wx_friends:
                if (!UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.WEIXIN)) {
                    ArmsUtils.makeText(App.getInstance(), "您没有安装微信");
                    return;
                }
                ShareAction shareAction2 = new ShareAction(this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(new ShareListener());
                if (position == 0) shareAction2.withMedia(web).share();
                else shareAction2.withMedia(umImage).share();
                break;
            case R.id.tv_qq:
                if (!UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.QQ)) {
                    ArmsUtils.makeText(App.getInstance(), "您没有安装QQ");
                    return;
                }
                ShareAction shareAction3 = new ShareAction(this).setPlatform(SHARE_MEDIA.QQ)
                        .setCallback(new ShareListener());
                if (position == 0) shareAction3.withMedia(web).share();
                else shareAction3.withMedia(umImage).share();

                break;
            case R.id.tv_qq_zone:
                if (!UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.QQ)) {
                    ArmsUtils.makeText(App.getInstance(), "您没有安装QQ");
                    return;
                }
                ShareAction shareAction4 = new ShareAction(this).setPlatform(SHARE_MEDIA.QZONE)
                        .setCallback(new ShareListener());
                if (position == 0) shareAction4.withMedia(web).share();
                else shareAction4.withMedia(umImage).share();
                break;
            case R.id.tv_cancel:
                break;
        }
    }

    public static class ShareListener implements UMShareListener {

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ArmsUtils.makeText(App.getInstance(), "分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            ArmsUtils.makeText(App.getInstance(), "分享已取消");
        }
    }

    private void addQRToImage(Bitmap srcBitmap) {
        Bitmap srcNew = BitmapUtils.zoomBitmap(srcBitmap, 1242, 2209);
        Bitmap qrNew = BitmapUtils.zoomBitmap(qrCode, 397, 397);
        Bitmap shareNew = BitmapUtils.addLogoToQRCode(srcNew, qrNew);
        File file = BitmapUtils.bitmap2File(shareNew,
                new File(DataHelper.getCacheFile(this),"zhmg_share_20180929.jpg"));
        Timber.i("addQRToImage: "+file.getAbsolutePath()+","+file.length());
        UserQRActivity.this.shareFile=file;
    }
}
