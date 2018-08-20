package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerMerchantInfoComponent;
import com.wta.NewCloudApp.di.module.MerchantInfoModule;
import com.wta.NewCloudApp.manager.IconSelector;
import com.wta.NewCloudApp.manager.LocationManager;
import com.wta.NewCloudApp.mvp.contract.MerchantInfoContract;
import com.wta.NewCloudApp.mvp.model.entity.BClass;
import com.wta.NewCloudApp.mvp.model.entity.BType;
import com.wta.NewCloudApp.mvp.model.entity.ErrorBusiness;
import com.wta.NewCloudApp.mvp.presenter.MerchantInfoPresenter;
import com.wta.NewCloudApp.mvp.ui.adapter.ClassAdapter;
import com.wta.NewCloudApp.mvp.ui.listener.DetDialogCallback;
import com.wta.NewCloudApp.mvp.ui.listener.DialogCallback;
import com.wta.NewCloudApp.mvp.ui.widget.EditTextHint;
import com.wta.NewCloudApp.mvp.ui.widget.link_with4_class.City;
import com.wta.NewCloudApp.mvp.ui.widget.link_with4_class.County;
import com.wta.NewCloudApp.mvp.ui.widget.link_with4_class.LinkDialog;
import com.wta.NewCloudApp.mvp.ui.widget.link_with4_class.OnAddressSelectedListener;
import com.wta.NewCloudApp.mvp.ui.widget.link_with4_class.Province;
import com.wta.NewCloudApp.mvp.ui.widget.link_with4_class.Street;
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

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 店铺详情
 */
public class MerchantInfoActivity extends BaseLoadingActivity<MerchantInfoPresenter> implements MerchantInfoContract.View,
        TakePhoto.TakeResultListener, InvokeListener {

    @BindView(R.id.et_name)
    EditTextHint etName;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_loc_4)
    TextView tvLoc4;
    @BindView(R.id.im_head)
    ImageView imHead;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.btn_apply)
    Button btnApply;
    @BindView(R.id.tv_01)
    TextView tvNameStr;
    @BindView(R.id.tv_08)
    TextView tvHeadStr;
    private LinkDialog locationDialog;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private int provinceID = -2001;
    private int cityID = -2001;
    private int districitID = -2001;
    private int townID = -2001;
    private String imgHeadStr;
    private LocationManager locationManager;
    private IconSelector iconSelector;
    private double latitude;
    private double longitude;
    private TimePickerView startTimePicker;
    private TimePickerView endTimePicker;
    private List<BClass> classes = new ArrayList<>();
    private BType type;
    private int iType;//6未入住店铺 1店铺详情错误 3店铺资质和详情错误 5店铺详情未填写
    private BClass bClass;
    private ErrorBusiness errorBusiness;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMerchantInfoComponent
                .builder()
                .appComponent(appComponent)
                .merchantInfoModule(new MerchantInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_merchant_info;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        iType = getIntent().getIntExtra("type", 6);
        if (iType == 6 || iType == 5) {
            startLocation();
        } else if (iType == 1 || iType == 3) {
            mPresenter.getErrorStore();
        }
    }

    public static void startInfo(Activity activity, int type) {
        Intent intent = new Intent(activity, MerchantInfoActivity.class);
        intent.putExtra("type", type);
        activity.startActivityForResult(intent, FinalUtils.REQUEST_BINFO);
    }

    private void startLocation() {
        if (locationManager == null) {
            locationManager = new LocationManager(this, new LocationManager.LocateListener() {
                @Override
                public void onLocateSuccess(AMapLocation location) {
                    tvLocation.setText(location.getAddress());
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }

                @Override
                public boolean onLocateFailed(AMapLocation location) {
                    return false;
                }
            });
        }
        locationManager.start();
    }

    @OnClick({R.id.lat_name, R.id.lat_start_time, R.id.lat_end_time, R.id.lat_type, R.id.lat_class, R.id.lat_location
            , R.id.lat_loc_4, R.id.btn_apply, R.id.im_head, R.id.tv_protocol})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lat_name:
                break;
            case R.id.lat_start_time:
                if (errorBusiness == null)
                    showStartTimePicker();
                break;
            case R.id.lat_end_time:
                if (errorBusiness == null)
                    showEndTimePicker();
                break;
            case R.id.lat_type:
                if (errorBusiness == null)
                    MerchantTypeActivity.startType(this, tvType.getText().toString());
                break;
            case R.id.lat_class:
                if (errorBusiness == null) {
                    if (classes.size() > 0) showClassPop();
                    else mPresenter.getBClass();
                }
                break;
            case R.id.lat_location:
                break;
            case R.id.lat_loc_4:
                if (errorBusiness == null)
                    showLocationDialog();
                break;
            case R.id.btn_apply:
                if (TextUtils.isEmpty(etName.getText())) {
                    showToast("请输入商户名称");
                    return;
                }
                if (TextUtils.isEmpty(tvStartTime.getText())) {
                    showToast("请选择开始时间");
                    return;
                }
                if (TextUtils.isEmpty(tvEndTime.getText())) {
                    showToast("请选择结束时间");
                    return;
                }
                if (TextUtils.isEmpty(tvType.getText())) {
                    showToast("请选择主营类目");
                    return;
                }
                if (TextUtils.isEmpty(tvClass.getText())) {
                    showToast("请选择店铺级别");
                    return;
                }
                if (TextUtils.isEmpty(tvLocation.getText())) {
                    showToast("没有店铺定位");
                    return;
                }
                if (TextUtils.isEmpty(tvLoc4.getText())) {
                    showToast("请选择所在区县");
                    return;
                }
                if (TextUtils.isEmpty(imgHeadStr)) {
                    showToast("请上传门头照");
                    return;
                }
                if (!checkBox.isChecked()) {
                    showToast("请阅读商户协议");
                    return;
                }
                //6未入住店铺 1店铺详情错误 3店铺资质和详情错误 5店铺详情未填写
                mPresenter.addStoreInfo(etName.getText().toString()
                        , (iType == 6 || iType == 5) ? type.type_id : errorBusiness.store.shop_type
                        , (iType == 6 || iType == 5) ? bClass.level_id : errorBusiness.store.shop_level
                        , (iType == 6 || iType == 5) ? latitude : errorBusiness.store.shop_address_x
                        , (iType == 6 || iType == 5) ? longitude : errorBusiness.store.shop_address_y
                        , tvStartTime.getText().toString(), tvEndTime.getText().toString()
                        , (iType == 6 || iType == 5) || errorBusiness == null || errorBusiness.store.shop_doorhead.status == 0 ? imgHeadStr : errorBusiness.store.shop_doorhead.info
                        , (iType == 6 || iType == 5) ? provinceID : errorBusiness.store.province
                        , (iType == 6 || iType == 5) ? cityID : errorBusiness.store.city
                        , (iType == 6 || iType == 5) ? districitID : errorBusiness.store.district
                        , (iType == 6 || iType == 5) ? townID : errorBusiness.store.twon
                        , tvLocation.getText().toString()
                        , tvLoc4.getText().toString());
                break;
            case R.id.im_head:
                if (iconSelector == null) {
                    iconSelector = new IconSelector(this, takePhoto, "zhmg_head.jpg", getCompressConfig(), getCropConfig());
                }
                iconSelector.showIconSelector();
                break;
            case R.id.tv_protocol:
                WebViewActivity.start(this, "入驻协议", FinalUtils.BUSINESS_PROTOCOL);
                break;
        }
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

    private void showLocationDialog() {
        if (locationDialog == null) {
            locationDialog = new LinkDialog(this);
            locationDialog.setOnAddressSelectedListener(new OnAddressSelectedListener() {
                @Override
                public void onAddressSelected(Province province, City city, County county, Street street) {
                    locationDialog.dismiss();
                    tvLoc4.setText(String.format("%s%s%s%s", province.getName(), city.getName(), county.getName(), street.getName()));
                    provinceID = province.getId();
                    cityID = city.getId();
                    districitID = county.getId();
                    townID = street.getId();
                }
            });
        }
        locationDialog.show();
    }

    @Override
    public void takeSuccess(TResult tResult) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String compressPath = tResult.getImage().getCompressPath();
                imHead.setImageBitmap(BitmapFactory.decodeFile(compressPath));
                imgHeadStr = EncodeUtils.fileToBase64(new File(compressPath));
            }
        });
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
            type = ((BType) data.getSerializableExtra("tag"));
            tvType.setText(type.type_name);
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


    @Override
    public void addSuccess() {
        DialogUtils.showAlertDialogWithOne(this, "资料审核中", new DetDialogCallback() {
            @Override
            public void handleLeft(Dialog dialog) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    public void getType(List<BClass> classes) {
        if (classes != null && classes.size() > 0) {
            this.classes.clear();
            this.classes.addAll(classes);
            showClassPop();
        }
    }

    private void showClassPop() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.b_class_layout);
        RecyclerView rv = dialog.findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ClassAdapter adapter = new ClassAdapter(R.layout.b_class_item, classes);
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                dialog.dismiss();
                bClass = ((BClass) adapter.getItem(position));
                tvClass.setText(bClass.level_name);
            }
        });
        dialog.show();
    }

    @Override
    public void getStoreErrorMsg(ErrorBusiness errorBusiness) {
        this.errorBusiness = errorBusiness;
        ErrorBusiness.StoreBean store = errorBusiness.store;
        etName.setText(store.shop_name.info);
        etName.setSelection(etName.length());
        if (store.shop_name.status == 0)
            tvNameStr.setTextColor(getResources().getColor(R.color.style_color));
        tvStartTime.setText(store.start_time);
        tvEndTime.setText(store.end_time);
        tvType.setText(store.type_name);
        tvClass.setText(store.level_name);
        tvLocation.setText(store.location_address);
        tvLoc4.setText(store.address);
        GlideArms.with(this).load(store.shop_doorhead.info).placeholder(R.mipmap.b_head_placeholder).into(imHead);
        if (store.shop_doorhead.status == 0)
            tvHeadStr.setTextColor(getResources().getColor(R.color.style_color));
        else {
            imgHeadStr = store.shop_doorhead.info;
            imHead.setEnabled(false);
        }
    }
}
