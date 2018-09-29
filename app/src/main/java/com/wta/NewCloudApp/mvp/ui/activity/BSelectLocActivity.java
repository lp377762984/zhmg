package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.ui.adapter.LocItemAdapter;
import com.wta.NewCloudApp.mvp.ui.widget.EditTextHint;
import com.wta.NewCloudApp.mvp.ui.widget.MoneyBar;
import com.wta.NewCloudApp.uitls.DialogUtils;
import com.wta.NewCloudApp.uitls.FinalUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import timber.log.Timber;

/**
 * 选择定位
 */
public class BSelectLocActivity extends BaseActivity implements TextWatcher, Inputtips.InputtipsListener, LocationSource, AMapLocationListener, GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener {
    @BindView(R.id.map)
    MapView mapView;
    @BindView(R.id.et_keywords)
    EditTextHint etKeywords;
    @BindView(R.id.mb)
    MoneyBar mb;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    LocItemAdapter adapter;
    private AMap aMap;
    private LatLonPoint searchLatlonPoint;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private boolean isItemClickAction;
    private boolean isInputKeySearch;
    private GeocodeSearch geocoderSearch;
    private Dialog waitDialog;
    private Marker locationMarker;
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;
    private String city;
    private PoiItem firstPoiItem;
    private int position = -1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bselect_loc;
    }

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, BSelectLocActivity.class);
        activity.startActivityForResult(intent, FinalUtils.REQUEST_LOC);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mb.setCallBack(mb.new CallbackImp() {
            @Override
            public void clickTail() {
                Intent intent = getIntent();
                intent.putExtra("poiItem", position == -1 ? firstPoiItem : adapter.getItem(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<PoiItem> data = new ArrayList<>();
        adapter = new LocItemAdapter(R.layout.loc_item, data);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                BSelectLocActivity.this.position = position;
                //searchPoi((PoiItem) adapter.getItem(position));
                Intent intent = getIntent();
                intent.putExtra("poiItem",(PoiItem) adapter.getItem(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        recyclerView.setAdapter(adapter);
        etKeywords.addTextChangedListener(this);
        mapView.onCreate(savedInstanceState);
        initMap();
    }

    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                Timber.i("onCameraChangeFinish: ");
                position = -1;
                if (!isItemClickAction && !isInputKeySearch) {
                    geoAddress();
                    //startJumpAnimation();
                }
                searchLatlonPoint = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
                isInputKeySearch = false;
                isItemClickAction = false;
            }
        });

        aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                Timber.i("onMapLoaded: ");
                addMarkerInScreenCenter(null);
            }
        });
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        hideSoftKey(etKeywords);
    }

    private void setUpMap() {
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String newText = s.toString().trim();
        if (newText.length() > 0) {
            InputtipsQuery inputquery = new InputtipsQuery(newText, city);
            Timber.i("afterTextChanged: " + city);
            Inputtips inputTips = new Inputtips(BSelectLocActivity.this, inputquery);
            inputquery.setCityLimit(true);
            inputTips.setInputtipsListener(this);
            inputTips.requestInputtipsAsyn();
        }
    }

    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        Timber.i("onGetInputtips: ");
        adapter.setNewData(tipsToPois(list));
    }

    private List<PoiItem> tipsToPois(List<Tip> tips) {
        List<PoiItem> poiItems = new ArrayList<>();
        for (int i = 0; i < tips.size(); i++) {
            Tip tip = tips.get(i);
            PoiItem poiItem = new PoiItem(null, tip.getPoint(), tip.getName(), tip.getAddress());
            poiItems.add(poiItem);
        }
        return poiItems;
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setOnceLocation(true);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    private void addMarkerInScreenCenter(LatLng locationLatLng) {
        LatLng latLng = aMap.getCameraPosition().target;
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
        locationMarker = aMap.addMarker(new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.loc_pin)));
        //设置Marker在屏幕上,不跟随地图移动
        locationMarker.setPositionByPixels(screenPosition.x, screenPosition.y);
        locationMarker.setZIndex(1);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        Timber.i("onLocationChanged: ");
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);
                LatLng curLatlng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                city = amapLocation.getCity();
                searchLatlonPoint = new LatLonPoint(curLatlng.latitude, curLatlng.longitude);
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLatlng, 16f));
                isInputKeySearch = false;
                etKeywords.setText("");
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        Timber.i("onRegeocodeSearched: ");
        hideDialog();
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                //city = result.getRegeocodeAddress().getCity();
                //String address = result.getRegeocodeAddress().getProvince() + result.getRegeocodeAddress().getCity() + result.getRegeocodeAddress().getDistrict() + result.getRegeocodeAddress().getTownship();
                String address = result.getRegeocodeAddress().getFormatAddress();
                if (firstPoiItem == null)
                    firstPoiItem = new PoiItem("regeo", searchLatlonPoint, address, address);
                Timber.i("onRegeocodeSearched: poiItem");
                doSearchQuery();
            }
        } else {
            Toast.makeText(BSelectLocActivity.this, "error code is " + rCode, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    /**
     * 响应逆地理编码
     */
    public void geoAddress() {
        showDialog();
        etKeywords.setText("");
        if (searchLatlonPoint != null) {
            RegeocodeQuery query = new RegeocodeQuery(searchLatlonPoint, 2000, GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
            geocoderSearch.getFromLocationAsyn(query);
        }
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        currentPage = 0;
        query = new PoiSearch.Query(etKeywords.getText().toString(), "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setCityLimit(true);
        query.setPageSize(20);
        query.setPageNum(currentPage);

        if (searchLatlonPoint != null) {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(searchLatlonPoint, 1000, true));//
            poiSearch.searchPOIAsyn();
        }
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int resultCode) {
        Timber.i("onPoiSearched: ");
        if (resultCode == AMapException.CODE_AMAP_SUCCESS) {
            if (poiResult != null && poiResult.getQuery() != null) {
                if (poiResult.getQuery().equals(query)) {
                    List<PoiItem> poiItems = poiResult.getPois();
                    if (poiItems != null && poiItems.size() > 0) {
                        adapter.setNewData(poiItems);
                    } else {
                        Toast.makeText(BSelectLocActivity.this, "无搜索结果", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(BSelectLocActivity.this, "无搜索结果", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    private void searchPoi(PoiItem poiItem) {
        searchLatlonPoint = poiItem.getLatLonPoint();
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(searchLatlonPoint.getLatitude(), searchLatlonPoint.getLongitude()), 16f));
        hideSoftKey(etKeywords);
        doSearchQuery();
    }

    private void hideSoftKey(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mlocationClient) mlocationClient.onDestroy();
        if (mapView != null) mapView.onDestroy();
        hideDialog();
    }

    private void showDialog() {
        if (waitDialog == null)
            waitDialog = DialogUtils.createWaitDialog(this);
        else {
            if (!waitDialog.isShowing() && !isFinishing())
                waitDialog.show();
        }
    }

    private void hideDialog() {
        if (waitDialog != null && waitDialog.isShowing())
            waitDialog.dismiss();
    }
}
