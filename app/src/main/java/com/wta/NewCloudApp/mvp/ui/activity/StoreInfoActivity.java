package com.wta.NewCloudApp.mvp.ui.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerStoreInfoComponent;
import com.wta.NewCloudApp.di.module.StoreInfoModule;
import com.wta.NewCloudApp.manager.IconSelector;
import com.wta.NewCloudApp.mvp.contract.StoreInfoContract;
import com.wta.NewCloudApp.mvp.model.entity.BClass;
import com.wta.NewCloudApp.mvp.model.entity.BType;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.presenter.StoreInfoPresenter;
import com.wta.NewCloudApp.mvp.ui.adapter.ClassAdapter;
import com.wta.NewCloudApp.mvp.ui.widget.MoneyBar;
import com.wta.NewCloudApp.uitls.BitmapUtils;
import com.wta.NewCloudApp.uitls.DialogUtils;
import com.wta.NewCloudApp.uitls.EncodeUtils;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;


public class StoreInfoActivity extends BaseLoadingActivity<StoreInfoPresenter> implements StoreInfoContract.View, TakePhoto.TakeResultListener, InvokeListener {

    @BindView(R.id.mb)
    MoneyBar mb;
    @BindView(R.id.im_head)
    RoundedImageView imHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.im_store_01)
    RoundedImageView imStore01;
    @BindView(R.id.im_store_02)
    RoundedImageView imStore02;
    @BindView(R.id.im_store_03)
    RoundedImageView imStore03;
    @BindView(R.id.im_add_01)
    ImageView imAdd01;
    @BindView(R.id.lat_im_01)
    FrameLayout latIm01;
    @BindView(R.id.im_add_02)
    ImageView imAdd02;
    @BindView(R.id.lat_im_02)
    FrameLayout latIm02;
    @BindView(R.id.im_add_03)
    ImageView imAdd03;
    @BindView(R.id.lat_im_03)
    FrameLayout latIm03;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private Business business;
    private IconSelector iconSelector;
    private boolean isChanged;//是否有内容改变
    private int position;//当前点击的哪张图片 0 head 1 店内实景照片1 2 店内实景照片2 3 店内实景照片3
    private TimePickerView startTimePicker;
    private TimePickerView endTimePicker;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStoreInfoComponent
                .builder()
                .appComponent(appComponent)
                .storeInfoModule(new StoreInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_store_info;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.getAllStoreInfo();
        mb.setCallBack(mb.new CallbackImp() {
            @Override
            public void clickTail() {
                mPresenter.modifyStore(business.shop_doorhead, tvStartTime.getText().toString()
                        , tvEndTime.getText().toString()
                        , business.code_type
                        , tvPhone.getText().toString()
                        , tvDesc.getText().toString()
                        , buildPictureStr());
            }
        });
    }

    private TreeMap<String, Object> buildPictureStr() {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("image1", business.picture.image1);
        map.put("image2", business.picture.image2);
        map.put("image3", business.picture.image3);
        return map;
    }


    @OnClick({R.id.lat_head, R.id.lat_start_time, R.id.lat_end_time, R.id.lat_type, R.id.lat_phone, R.id.lat_desc, R.id.im_store_01, R.id.im_store_02, R.id.im_store_03})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lat_head:
                position = 0;
                selectIcon();
                break;
            case R.id.lat_start_time:
                showStartTimePicker();
                break;
            case R.id.lat_end_time:
                showEndTimePicker();
                break;
            case R.id.lat_type:
                MerchantTypeActivity.startType(this, tvType.getText().toString());
                break;
            case R.id.lat_phone:
                StorePhoneActivity.startPhone(this, tvPhone.getText().toString());
                break;
            case R.id.lat_desc:
                StoreDescActivity.startDesc(this, tvDesc.getText().toString());
                break;
            case R.id.im_store_01:
                position = 1;
                selectIcon();
                break;
            case R.id.im_store_02:
                position = 2;
                selectIcon();
                break;
            case R.id.im_store_03:
                position = 3;
                selectIcon();
                break;
        }
    }

    private void selectIcon() {
        if (iconSelector == null) {
            iconSelector = new IconSelector(this, takePhoto, "zhmg_head.jpg", getCompressConfig(), getCropConfig());
        }
        iconSelector.showIconSelector();
    }

    @Override
    public void showStoreInfo(Business data) {
        this.business = data;
        GlideArms.with(this).load(data.shop_doorhead).into(imHead);
        tvName.setText(data.shop_name);
        tvClass.setText(data.level_name);
        tvLocation.setText(data.location_address);
        tvStartTime.setText(data.start_time);
        tvEndTime.setText(data.end_time);
        tvType.setText(data.type_name);
        tvPhone.setText(data.telephone);
        tvDesc.setText(data.introduction);
        Business.PictureBean picture = data.picture;
        if (picture != null && !TextUtils.isEmpty(picture.image1)) {//第一张有图片
            GlideArms.with(this).load(picture.image1).into(imStore01);
            imAdd01.setVisibility(View.GONE);
        } else {
            imAdd01.setVisibility(View.VISIBLE);
        }
        if (picture != null && !TextUtils.isEmpty(picture.image2)) {//第二张有图片
            GlideArms.with(this).load(picture.image2).into(imStore02);
            imAdd02.setVisibility(View.GONE);
        } else {
            imAdd02.setVisibility(View.VISIBLE);
        }
        if (picture != null && !TextUtils.isEmpty(picture.image2)) {//第三张有图片
            GlideArms.with(this).load(picture.image3).into(imStore03);
            imAdd03.setVisibility(View.GONE);
        } else {
            imAdd03.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void saveSuccess(Business data) {
        showToast("修改成功");
        mPresenter.getAllStoreInfo();
    }

    @Override
    public void takeSuccess(TResult tResult) {
        String compressPath = tResult.getImage().getCompressPath();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (position) {
                    case 0:
                        imHead.setImageBitmap(BitmapUtils.scaleBitmap(compressPath, 270, 134));
                        business.shop_doorhead = EncodeUtils.fileToBase64(new File(compressPath));
                        isChanged = true;
                        break;
                    case 1:
                        imStore01.setImageBitmap(BitmapUtils.scaleBitmap(compressPath, 270, 134));
                        business.picture.image1 = EncodeUtils.fileToBase64(new File(compressPath));
                        isChanged = true;
                        break;
                    case 2:
                        imStore02.setImageBitmap(BitmapUtils.scaleBitmap(compressPath, 270, 134));
                        business.picture.image2 = EncodeUtils.fileToBase64(new File(compressPath));
                        isChanged = true;
                        break;
                    case 3:
                        imStore03.setImageBitmap(BitmapUtils.scaleBitmap(compressPath, 270, 134));
                        business.picture.image3 = EncodeUtils.fileToBase64(new File(compressPath));
                        isChanged = true;
                        break;
                }
            }
        });
    }

    private void showStartTimePicker() {
        if (startTimePicker == null) {
            startTimePicker = DialogUtils.showTimePicker(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    tvStartTime.setText(getTime(date));
                }
            });
        }
        startTimePicker.show();
    }

    private void showEndTimePicker() {
        if (endTimePicker == null) {
            endTimePicker = DialogUtils.showTimePicker(this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    tvEndTime.setText(getTime(date));
                }
            });
        }
        endTimePicker.show();
    }

    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return format.format(date);
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
        if (resultCode == RESULT_OK && requestCode == FinalUtils.REQUEST_TAG) {
            BType type = ((BType) data.getSerializableExtra("tag"));
            business.code_type = type.cate_id;
            business.type_name = type.type_name;
            isChanged = true;
            tvType.setText(business.type_name);
        } else if (resultCode == RESULT_OK && requestCode == FinalUtils.REQUEST_PHONE) {
            business.telephone = data.getStringExtra("phone");
            isChanged = true;
            tvPhone.setText(business.telephone);
        } else if (resultCode == RESULT_OK && requestCode == FinalUtils.REQUEST_DESC) {
            business.introduction = data.getStringExtra("desc");
            tvDesc.setText(business.introduction);
            isChanged = true;
        }
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

    private CompressConfig getCompressConfig() {
        int maxSize = 1024 * 500;
        LubanOptions option = new LubanOptions.Builder().setMaxSize(maxSize).create();
        return CompressConfig.ofLuban(option);
    }

    private CropOptions getCropConfig() {
        return new CropOptions.Builder().setWithOwnCrop(false).create();
    }

}
