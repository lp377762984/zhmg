package com.wta.NewCloudApp.mvp.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerMerchantAuthComponent;
import com.wta.NewCloudApp.di.module.MerchantAuthModule;
import com.wta.NewCloudApp.mvp.contract.MerchantAuthContract;
import com.wta.NewCloudApp.mvp.model.entity.AuthInfo;
import com.wta.NewCloudApp.mvp.presenter.MerchantAuthPresenter;
import com.wta.NewCloudApp.uitls.BitmapUtils;
import com.wta.NewCloudApp.uitls.EncodeUtils;
import com.wta.NewCloudApp.uitls.FinalUtils;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.LubanOptions;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;


public class MerchantAuthActivity extends BaseLoadingActivity<MerchantAuthPresenter> implements MerchantAuthContract.View,
        TakePhoto.TakeResultListener, InvokeListener, View.OnClickListener {

    @BindView(R.id.im_passport)
    ImageView imPassport;
    @BindView(R.id.im_hand_card)
    ImageView imHandCard;
    @BindView(R.id.im_card_positive)
    ImageView imCardPositive;
    @BindView(R.id.im_card_negative)
    ImageView imCardNegative;

    @BindView(R.id.tv_passport)
    TextView tvPassport;
    @BindView(R.id.tv_hand_card)
    TextView tvHandCard;
    @BindView(R.id.tv_card_positive)
    TextView tvCardPositive;
    @BindView(R.id.tv_card_negative)
    TextView tvCardNegative;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    BottomSheetDialog btmDialog;
    private int currentId;//当前点击的viewID
    private String passportImg;
    private String handImg;
    private String positiveImg;
    private String negativeImg;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMerchantAuthComponent
                .builder()
                .appComponent(appComponent)
                .merchantAuthModule(new MerchantAuthModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_merchant_auth;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    public static void startAuth(Activity activity) {
        Intent intent = new Intent(activity, MerchantAuthActivity.class);
        activity.startActivityForResult(intent, FinalUtils.REQUEST_BAUTH);
    }

    @OnClick({R.id.im_passport, R.id.im_hand_card, R.id.im_card_positive, R.id.im_card_negative, R.id.btn_next})
    public void onViewClicked(View view) {
        currentId = view.getId();
        switch (view.getId()) {
            case R.id.im_passport:
                showDialog();
                break;
            case R.id.im_hand_card:
                showDialog();
                break;
            case R.id.im_card_positive:
                showDialog();
                break;
            case R.id.im_card_negative:
                showDialog();
                break;
            case R.id.btn_next:
                if (TextUtils.isEmpty(passportImg)) {
                    showToast("请上传营业执照");
                    return;
                }
                if (TextUtils.isEmpty(handImg)) {
                    showToast("请上传法人手持身份证照片");
                    return;
                }
                if (TextUtils.isEmpty(positiveImg)) {
                    showToast("请上传法人正面身份证照片");
                    return;
                }
                if (TextUtils.isEmpty(negativeImg)) {
                    showToast("请上传法人反面身份证照片");
                    return;
                }
                mPresenter.applyAuth(passportImg, handImg, positiveImg, negativeImg);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        btmDialog.dismiss();
        switch (v.getId()) {
            case R.id.tv_album:
                new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (!aBoolean) showToast("没有权限无法继续操作");
                        else {
                            configCompress();
                            takePhoto.onPickFromGallery();
                        }
                    }
                });
                break;
            case R.id.tv_camera:
                new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (!aBoolean) showToast("没有权限无法继续操作");
                        else {
                            configCompress();
                            takePhoto.onPickFromCapture(getImageUri());
                        }
                    }
                });
                break;
            case R.id.tv_cancel:
                break;
        }
    }

    public void showDialog() {
        if (btmDialog == null) {
            btmDialog = new BottomSheetDialog(this);
            btmDialog.setContentView(R.layout.picuture_selector_lat);
            btmDialog.findViewById(R.id.tv_album).setOnClickListener(this);
            btmDialog.findViewById(R.id.tv_camera).setOnClickListener(this);
            btmDialog.findViewById(R.id.tv_cancel).setOnClickListener(this);
        }
        btmDialog.show();
    }

    @Override
    public void takeSuccess(TResult tResult) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String compressPath = tResult.getImage().getOriginalPath();
                File file = new File(compressPath);
                switch (currentId) {
                    case R.id.im_passport:
                        passportImg = EncodeUtils.fileToBase64(file);
                        setImageView(imPassport, tvPassport, compressPath);
                        break;
                    case R.id.im_hand_card:
                        handImg = EncodeUtils.fileToBase64(file);
                        setImageView(imHandCard, tvHandCard, compressPath);
                        break;
                    case R.id.im_card_positive:
                        positiveImg = EncodeUtils.fileToBase64(file);
                        setImageView(imCardPositive, tvCardPositive, compressPath);
                        break;
                    case R.id.im_card_negative:
                        negativeImg = EncodeUtils.fileToBase64(file);
                        setImageView(imCardNegative, tvCardNegative, compressPath);
                        break;
                }
            }
        });
    }

    private void setImageView(ImageView imageView, TextView textView, String compressPath) {
        imageView.setImageBitmap(BitmapUtils.scaleBitmap(compressPath, 280, 180));
        textView.setVisibility(View.GONE);
    }

    @Override
    public void takeFail(TResult tResult, String s) {

    }

    @Override
    public void takeCancel() {

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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * 获取TakePhoto实例
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    private Uri getImageUri() {
        String name = "zhmg.jpg";
        switch (currentId) {
            case R.id.im_passport:
                name = "zhmg_passport.jpg";
                break;
            case R.id.im_hand_card:
                name = "zhmg_hand_card.jpg";
                break;
            case R.id.im_card_positive:
                name = "zhmg_card_positive.jpg";
                break;
            case R.id.im_card_negative:
                name = "zhmg_card_negative.jpg";
                break;
        }
        File dir = new File(Environment.getExternalStorageDirectory(), "/temp/");
        if (!dir.exists()) dir.mkdir();
        File file = new File(dir, name);
        if (file.exists()) {
            file.delete();
        }
        return Uri.fromFile(file);
    }

    private void configCompress() {
        int maxSize = 1024 * 500;
        LubanOptions option = new LubanOptions.Builder().setMaxSize(maxSize).create();
        CompressConfig config = CompressConfig.ofLuban(option);
        takePhoto.onEnableCompress(config, false);
    }

    @Override
    public void uploadSuccess(AuthInfo data) {
        showToast("上传成功");
    }
}
