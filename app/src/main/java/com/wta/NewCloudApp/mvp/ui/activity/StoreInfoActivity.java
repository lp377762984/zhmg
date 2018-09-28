package com.wta.NewCloudApp.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerStoreInfoComponent;
import com.wta.NewCloudApp.di.module.StoreInfoModule;
import com.wta.NewCloudApp.manager.IconSelector;
import com.wta.NewCloudApp.mvp.contract.StoreInfoContract;
import com.wta.NewCloudApp.mvp.model.entity.BType;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.PictureC;
import com.wta.NewCloudApp.mvp.presenter.StoreInfoPresenter;
import com.wta.NewCloudApp.mvp.ui.adapter.PictureAdapter;
import com.wta.NewCloudApp.uitls.BitmapUtils;
import com.wta.NewCloudApp.uitls.DialogUtils;
import com.wta.NewCloudApp.uitls.EncodeUtils;
import com.wta.NewCloudApp.uitls.FileUtils;
import com.wta.NewCloudApp.uitls.FinalUtils;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.LubanOptions;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;


public class StoreInfoActivity extends BaseLoadingActivity<StoreInfoPresenter> implements StoreInfoContract.View, TakePhoto.TakeResultListener, InvokeListener {

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
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_location_det)
    TextView tvLocDet;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private Business business;
    private IconSelector iconSelector;
    private boolean isClickHead = true;//true 点击店铺封面，false 点击店铺相册
    private TimePickerView startTimePicker;
    private TimePickerView endTimePicker;
    private PictureAdapter adapter;
    private int maxCount;
    private int limit;//上传图片限制
    private PoiItem poiItem;

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
        initPictureRecyclerView();
        mPresenter.getAllStoreInfo();
        limit = maxCount = 6;
    }

    private void initPictureRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        List<PictureC> pictures = new ArrayList<>();
        adapter = new PictureAdapter(R.layout.picture_item, pictures);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PicDetActivity.startPicDet(StoreInfoActivity.this, (ArrayList<PictureC>) adapter.getData(), position);
            }
        });
        View footView = getLayoutInflater().inflate(R.layout.picture_foot, recyclerView, false);
        footView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClickHead = false;
                selectIcon();
            }
        });
        adapter.setFooterView(footView, -1, LinearLayout.HORIZONTAL);
        recyclerView.setAdapter(adapter);
    }

    public void saveChange(String head, String startTime, String endTime, Integer typeId, String phone, String desc,
                           List<PictureC> pictures, String location_address, String address_details, String shop_address_x, String shop_address_y) {
        if (pictures != null && pictures.size() > 0)
            for (int i = 0; i < pictures.size(); i++) {
                PictureC pictureC = pictures.get(i);
                if (pictureC.file != null) {
                    pictureC.url = EncodeUtils.fileToBase64(pictureC.file);
                    pictureC.file = null;
                }
            }
        Business business = new Business();
        business.shop_doorhead = head;
        business.start_time = startTime;
        business.end_time = endTime;
        if (typeId != null) business.shop_type = String.valueOf(typeId);
        business.telephone = phone;
        business.introduction = desc;
        business.picture = pictures;
        business.location_address = location_address;
        business.address_details = address_details;
        business.shop_address_x = shop_address_x;
        business.shop_address_y = shop_address_y;
        mPresenter.modifyStore(business);
    }


    @OnClick({R.id.lat_head, R.id.lat_start_time, R.id.lat_end_time, R.id.lat_type, R.id.lat_phone, R.id.lat_desc
            , R.id.lat_location, R.id.lat_location_det})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lat_location:
                BSelectLocActivity.start(this);
                break;
            case R.id.lat_location_det:
                StoreLocDetActivity.startLocDet(this, tvLocDet.getText().toString());
                break;
            case R.id.lat_head:
                isClickHead = true;
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
        }
    }

    private void selectIcon() {
        if (iconSelector == null) {
            iconSelector = new IconSelector(this, takePhoto, "zhmg_head.jpg", getCompressConfig(), getCropConfig());
        }
        if (isClickHead) iconSelector.limit = 1;
        else iconSelector.limit = limit;
        iconSelector.showIconSelector();
    }

    @Override
    public void showStoreInfo(Business data) {
        this.business = data;
        GlideArms.with(this).load(data.shop_doorhead).placeholder(R.mipmap.side_b_placeholder).into(imHead);
        tvName.setText(data.shop_name);
        tvClass.setText(data.level_name);
        tvLocation.setText(data.location_address);
        tvLocDet.setText(data.address_details);
        tvStartTime.setText(data.start_time);
        tvEndTime.setText(data.end_time);
        tvType.setText(data.type_name);
        tvPhone.setText(data.telephone);
        tvDesc.setText(data.introduction);
        limit = maxCount = data.img_sum;
        List<PictureC> pictureCList = data.picture;
        if (maxCount == pictureCList.size()) adapter.removeAllFooterView();
        adapter.setNewData(pictureCList);
    }

    @Override
    public void saveSuccess(Business data) {
        showToast("修改成功");
        if (data != null && data.picture != null && data.picture.size() > 0) {
            adapter.setNewData(data.picture);
        }
    }

    @Override
    public void takeSuccess(TResult tResult) {
        String compressPath = tResult.getImage().getCompressPath();
        ArrayList<TImage> images = tResult.getImages();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isClickHead) {//获取店铺封面
                    imHead.setImageBitmap(BitmapUtils.scaleBitmap(compressPath, 270, 134));
                    saveChange(EncodeUtils.fileToBase64(new File(compressPath)), null, null, null,
                            null, null, null, null, null, null, null);
                } else {//获取店铺相册
                    List<PictureC> addData = new ArrayList<>(images.size());
                    for (int i = 0; i < images.size(); i++) {
                        String path = images.get(i).getCompressPath();
                        PictureC pictureC = new PictureC();
                        pictureC.file = new File(path);
                        addData.add(pictureC);
                    }
                    adapter.addData(addData);
                    limit = limit - adapter.getData().size();
                    if (limit <= 0) {
                        adapter.removeAllFooterView();
                    }
                    saveChange(null, null, null, null, null, null, adapter.getData(), null, null, null, null);
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
                    saveChange(null, tvStartTime.getText().toString(), null, null, null, null, null, null, null, null, null);
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
                    saveChange(null, null, tvEndTime.getText().toString(), null, null, null, null, null, null, null, null);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == FinalUtils.REQUEST_TAG) {
            BType type = ((BType) data.getSerializableExtra("tag"));
            business.type_id = type.type_id;
            business.type_name = type.type_name;
            tvType.setText(business.type_name);
            saveChange(null, null, null, type.type_id, null, null, null
                    , null, null, null, null);
        } else if (resultCode == RESULT_OK && requestCode == FinalUtils.REQUEST_PHONE) {
            business.telephone = data.getStringExtra("phone");
            tvPhone.setText(business.telephone);
            saveChange(null, null, null, null, business.telephone, null, null,
                    null, null, null, null);
        } else if (resultCode == RESULT_OK && requestCode == FinalUtils.REQUEST_DESC) {
            business.introduction = data.getStringExtra("desc");
            tvDesc.setText(business.introduction);
            saveChange(null, null, null, null, null, business.introduction, null,
                    null, null, null, null);
        } else if (resultCode == RESULT_OK && requestCode == FinalUtils.REQUEST_PIC_DET) {
            if (data.getBooleanExtra("isNeedChange", false)) {
                ArrayList<PictureC> pictures = (ArrayList<PictureC>) data.getSerializableExtra("pictures");
                adapter.setNewData(pictures);
                saveChange(null, null, null, null, null, null, pictures,
                        null, null, null, null);
            }
        } else if (resultCode == RESULT_OK && requestCode == FinalUtils.REQUEST_LOC) {
            poiItem = data.getParcelableExtra("poiItem");
            String cityName = poiItem.getCityName();
            String adName = poiItem.getAdName();
            String snippet = poiItem.getSnippet();
            StringBuilder sb = new StringBuilder();
            if (!TextUtils.isEmpty(cityName) && !"null".equals(cityName)) sb.append(cityName);
            if (!TextUtils.isEmpty(adName) && !"null".equals(adName)) sb.append(adName);
            if (!TextUtils.isEmpty(snippet) && !"null".equals(snippet)) sb.append(snippet);
            business.location_address = sb.toString();
            tvLocation.setText(sb.toString());
            saveChange(null, null, null, null, null, null,
                    null, business.location_address, null,
                    poiItem.getLatLonPoint().getLatitude() + "",
                    poiItem.getLatLonPoint().getLongitude() + "");
        } else if (resultCode == RESULT_OK && requestCode == FinalUtils.REQUEST_LOC_DET) {
            String locDet = data.getStringExtra("locDet");
            business.address_details = locDet;
            tvLocDet.setText(locDet);
            saveChange(null, null, null, null, null, null,
                    null, null, business.address_details, null, null);

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
        return new CropOptions.Builder().setWithOwnCrop(false).setAspectX(90).setAspectY(67).create();
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
}
