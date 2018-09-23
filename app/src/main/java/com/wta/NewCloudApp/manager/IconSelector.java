package com.wta.NewCloudApp.manager;

import android.Manifest;
import android.app.Activity;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;

import com.jess.arms.utils.ArmsUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wta.NewCloudApp.R;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;

import java.io.File;

import io.reactivex.functions.Consumer;

public class IconSelector implements View.OnClickListener {
    private Activity activity;
    private String fileName;
    private CompressConfig compressConfig;
    private CropOptions cropOptions;
    private TakePhoto takePhoto;
    private BottomSheetDialog btmDialog;
    public int limit = 1;//默片限制个数

    public IconSelector(Activity activity, TakePhoto takePhoto, String fileName, CompressConfig compressConfig, CropOptions cropOptions) {
        this.activity = activity;
        this.takePhoto = takePhoto;
        this.fileName = fileName;
        this.compressConfig = compressConfig;
        this.cropOptions = cropOptions;
    }

    public IconSelector(Activity activity, TakePhoto takePhoto, String fileName, CompressConfig compressConfig) {
        this(activity, takePhoto, fileName, compressConfig, null);
    }

    public void showIconSelector() {
        if (btmDialog == null) {
            btmDialog = new BottomSheetDialog(activity);
            btmDialog.setContentView(R.layout.picuture_selector_lat);
            btmDialog.findViewById(R.id.tv_album).setOnClickListener(this);
            btmDialog.findViewById(R.id.tv_camera).setOnClickListener(this);
            btmDialog.findViewById(R.id.tv_cancel).setOnClickListener(this);
        }
        btmDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_album:
                btmDialog.dismiss();
                new RxPermissions(activity).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            configCompress();
                            if (cropOptions != null) {
                                if (limit > 1) {
                                    takePhoto.onPickMultipleWithCrop(limit, cropOptions);
                                } else {
                                    takePhoto.onPickFromGalleryWithCrop(getImageUri(), cropOptions);
                                }
                            } else {
                                if (limit > 1) {
                                    takePhoto.onPickMultiple(limit);
                                } else {
                                    takePhoto.onPickFromGallery();
                                }
                            }
                        } else {
                            ArmsUtils.makeText(activity, "没有权限不能修改头像");
                        }
                    }
                });
                break;
            case R.id.tv_camera:
                btmDialog.dismiss();
                new RxPermissions(activity).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            configCompress();
                            if (cropOptions != null)
                                takePhoto.onPickFromCaptureWithCrop(getImageUri(), cropOptions);
                            else takePhoto.onPickFromCapture(getImageUri());
                        } else {
                            ArmsUtils.makeText(activity, "没有权限不能修改头像");
                        }
                    }
                });
                break;
            case R.id.tv_cancel:
                btmDialog.dismiss();
                break;
        }
    }

    private Uri getImageUri() {
        File dir = new File(Environment.getExternalStorageDirectory(), "/temp/");
        if (!dir.exists()) dir.mkdir();
        File file = new File(dir, fileName);
        if (file.exists()) {
            file.delete();
        }
        return Uri.fromFile(file);
    }

    private void configCompress() {
        takePhoto.onEnableCompress(compressConfig, false);
    }
}
