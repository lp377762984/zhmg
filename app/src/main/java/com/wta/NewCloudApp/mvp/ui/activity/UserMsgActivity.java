package com.wta.NewCloudApp.mvp.ui.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.di.component.DaggerUserMsgComponent;
import com.wta.NewCloudApp.di.module.UserMsgModule;
import com.wta.NewCloudApp.jiuwei210278.R;
import com.wta.NewCloudApp.mvp.contract.UserMsgContract;
import com.wta.NewCloudApp.mvp.presenter.UserMsgPresenter;
import com.wta.NewCloudApp.uitls.ConfigTag;
import com.wta.NewCloudApp.uitls.DialogUtils;
import com.wta.NewCloudApp.uitls.FinalUtils;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.LubanOptions;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;


public class UserMsgActivity extends BaseLoadingActivity<UserMsgPresenter> implements UserMsgContract.View, View.OnClickListener, TakePhoto.TakeResultListener, InvokeListener {


    @BindView(R.id.im_head)
    ImageView imHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.im_code)
    RoundedImageView imCode;
    BottomSheetDialog btmDialog;
    @BindView(R.id.tv_state)
    TextView tvState;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserMsgComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .userMsgModule(new UserMsgModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_user_msg; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        GlideArms.with(this).load(AppConfig.getInstance().getString(ConfigTag.AVATAR, null))
                .placeholder(R.mipmap.user_default).into(imHead);
        tvName.setText(AppConfig.getInstance().getString(ConfigTag.NICKNAME, null));
        tvState.setText(AppConfig.getInstance().getInt(ConfigTag.CARD_STATUS, 0) == 0 ? "待认证" : "已认证");
    }

    @OnClick({R.id.lat_head, R.id.lat_name, R.id.lat_code, R.id.lat_real})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lat_head:
                if (btmDialog == null) {
                    btmDialog = new BottomSheetDialog(this);
                    btmDialog.setContentView(R.layout.picuture_selector_lat);
                    btmDialog.findViewById(R.id.tv_album).setOnClickListener(this);
                    btmDialog.findViewById(R.id.tv_camera).setOnClickListener(this);
                    btmDialog.findViewById(R.id.tv_cancel).setOnClickListener(this);
                }
                btmDialog.show();
                break;
            case R.id.lat_name:
                NameSetActivity.start(this, tvName.getText().toString());
                break;
            case R.id.lat_code:
                ArmsUtils.startActivity(UserQRActivity.class);
                break;
            case R.id.lat_real:
                if (AppConfig.getInstance().getInt(ConfigTag.CARD_STATUS, 0) == 0)
                    ArmsUtils.makeText(this, "跳认证界面");
                else
                    DialogUtils.createAuthDialog(this).show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_album:
                btmDialog.dismiss();
                PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
                    @Override
                    public void onRequestPermissionSuccess() {
                        configCompress();
                        takePhoto.onPickFromGalleryWithCrop(getImageUri(), getCropConfig());
                    }

                    @Override
                    public void onRequestPermissionFailure(List<String> permissions) {
                        ArmsUtils.makeText(UserMsgActivity.this, "没有权限不能修改头像");
                    }

                    @Override
                    public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {

                    }
                }, new RxPermissions(this), mPresenter.mErrorHandler);
                break;
            case R.id.tv_camera:
                btmDialog.dismiss();
                PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
                    @Override
                    public void onRequestPermissionSuccess() {
                        configCompress();
                        takePhoto.onPickFromCaptureWithCrop(getImageUri(), getCropConfig());
                    }

                    @Override
                    public void onRequestPermissionFailure(List<String> permissions) {
                        ArmsUtils.makeText(UserMsgActivity.this, "没有权限不能修改头像");
                    }

                    @Override
                    public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {

                    }
                }, new RxPermissions(this), mPresenter.mErrorHandler);

                break;
            case R.id.tv_cancel:
                btmDialog.dismiss();
                break;
        }
    }

    private Uri getImageUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Uri.fromFile(file);
    }

    private CropOptions getCropConfig() {
        return new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == FinalUtils.REQUEST_SET_NAME) {
            tvName.setText(data.getStringExtra("name"));
            mPresenter.setUser(data.getStringExtra("name"), null, 0);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        Timber.d("takeSuccess：%s", result.getImage().getCompressPath());
        runOnUiThread(new Runnable() {// takeSuccess不在主线程
            @Override
            public void run() {
                imHead.setImageBitmap(BitmapFactory.decodeFile(result.getImage().getCompressPath()));
            }
        });
        mPresenter.setUser(null, new File(result.getImage().getCompressPath()), 1);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Timber.d("takeFail:%s", msg);
    }

    @Override
    public void takeCancel() {
        Timber.d("takeCancel: ");
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    private void configCompress() {
        int maxSize = 1024 * 300;
        int width = 512;
        int height = 512;
        LubanOptions option = new LubanOptions.Builder().setMaxHeight(height).setMaxWidth(width).setMaxSize(maxSize).create();
        CompressConfig config = CompressConfig.ofLuban(option);
        takePhoto.onEnableCompress(config, false);
    }

}
