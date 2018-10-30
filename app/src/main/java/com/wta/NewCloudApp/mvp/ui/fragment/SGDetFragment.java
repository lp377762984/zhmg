package com.wta.NewCloudApp.mvp.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.utils.ArmsUtils;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.di.component.DaggerSGDetComponent;
import com.wta.NewCloudApp.di.module.SGDetModule;
import com.wta.NewCloudApp.mvp.contract.SGDetContract;
import com.wta.NewCloudApp.mvp.model.entity.Address;
import com.wta.NewCloudApp.mvp.model.entity.SGDet;
import com.wta.NewCloudApp.mvp.presenter.SGDetPresenter;
import com.wta.NewCloudApp.mvp.ui.activity.AddressListActivity;
import com.wta.NewCloudApp.mvp.ui.activity.ExSucActivity;
import com.wta.NewCloudApp.mvp.ui.activity.LoginActivity;
import com.wta.NewCloudApp.mvp.ui.activity.SGDetActivity;
import com.wta.NewCloudApp.mvp.ui.activity.SideDetActivity;
import com.wta.NewCloudApp.mvp.ui.activity.StoreGoodsListActivity;
import com.wta.NewCloudApp.mvp.ui.listener.DetDialogCallback;
import com.wta.NewCloudApp.mvp.ui.widget.NumberBtn;
import com.wta.NewCloudApp.mvp.ui.widget.PJImageLoader;
import com.wta.NewCloudApp.mvp.ui.widget.vertical_drag.CustScrollView;
import com.wta.NewCloudApp.uitls.ConfigTag;
import com.wta.NewCloudApp.uitls.DialogUtils;
import com.wta.NewCloudApp.uitls.FinalUtils;
import com.wta.NewCloudApp.uitls.NumberFormatUtils;
import com.wta.NewCloudApp.uitls.ScreenDpiUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 礼品详情
 */
public class SGDetFragment extends BaseLoadingFragment<SGDetPresenter> implements SGDetContract.View, NumberBtn.BtnListener {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_left_count)
    TextView tvLeftCount;
    @BindView(R.id.tv_express_fee)
    TextView tvExpressFee;

    @BindView(R.id.number_btn)
    NumberBtn numberBtn;

    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_mobile)
    TextView tvUserMobile;
    @BindView(R.id.tv_user_address)
    TextView tvUserAddress;

    @BindView(R.id.im_b_logo)
    ImageView imBLogo;
    @BindView(R.id.tv_b_name)
    TextView tvBName;

    @BindView(R.id.tv_attention)
    TextView tvAttention;

    @BindView(R.id.lat_b_det)
    View latBDet;
    @BindView(R.id.lat_has_address)
    View latHasAdd;
    @BindView(R.id.lat_no_address)
    View latNoAdd;

    @BindView(R.id.scrollView)
    CustScrollView scrollView;

    private int goodsId;
    private int type;//1线上 2线下
    private SGDet sgDet;
    private int currentNum = 1;
    private int addressId;

    public static SGDetFragment getInstance(int goodsId, int type) {
        SGDetFragment fragment = new SGDetFragment();
        Bundle b = new Bundle();
        b.putInt("goods_id", goodsId);
        b.putInt("type", type);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSGDetComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .sGDetModule(new SGDetModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sgdet, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        goodsId = getArguments().getInt("goods_id");
        type = getArguments().getInt("type");
        //calculateHeight();
        numberBtn.setListener(this);
        mPresenter.getSGDet(type, goodsId);
    }

    private void calculateHeight() {
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int heightPixels = getResources().getDisplayMetrics().heightPixels;
        int statusBarHeight = ScreenDpiUtils.getStatusBarHeight(getContext());
        FrameLayout latTitle = ((SGDetActivity) getActivity()).latTitle;
        latTitle.setBackgroundResource(R.color.transparent);
        FrameLayout bottomLay = getActivity().findViewById(R.id.bottom);
        ViewGroup.LayoutParams params = bottomLay.getLayoutParams();
        params.height = heightPixels
                - (int) ScreenDpiUtils.dp2px(getContext(), 48)
                - (int) ScreenDpiUtils.dp2px(getContext(), 48);
        if (heightPixels > widthPixels)
            params.height = params.height - statusBarHeight;
        //final int height = widthPixels - statusBarHeight - (int) ScreenDpiUtils.dp2px(getContext(), 48);
        final int height = (int) ScreenDpiUtils.dp2px(getContext(), 48);
        scrollView.setScrollListener(new CustScrollView.ScrollListener() {
            @Override
            public void onScrollChange(int l, int t, int oldl, int oldt) {
                if (t <= height) {
                    latTitle.setBackgroundColor(Color.argb(t * 255 / height, 255, 76, 106));
                } else {
                    latTitle.setBackgroundResource(R.color.style_color);
                }
            }
        });
    }

    @OnClick({R.id.lat_has_address, R.id.lat_no_address, R.id.gift_more, R.id.im_b_logo, R.id.tv_b_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lat_has_address:
                AddressListActivity.start(this, 1);
                break;
            case R.id.lat_no_address:
                if (!AppConfig.getInstance().getBoolean(ConfigTag.IS_LOGIN, false))
                    ArmsUtils.startActivity(LoginActivity.class);
                else
                    AddressListActivity.start(this, 1);
                break;
            case R.id.gift_more:
                StoreGoodsListActivity.start(getActivity(), sgDet.shop_id);
                break;
            case R.id.im_b_logo:
                SideDetActivity.startDet(getActivity(), sgDet.shop_id);
                break;
            case R.id.tv_b_name:
                SideDetActivity.startDet(getActivity(), sgDet.shop_id);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FinalUtils.REQUEST_ADDRESS && resultCode == Activity.RESULT_OK) {
            latNoAdd.setVisibility(View.GONE);
            latHasAdd.setVisibility(View.VISIBLE);
            Address address = (Address) data.getSerializableExtra("address");
            tvUserName.setText(address.consignee);
            tvUserMobile.setText(address.mobile);
            tvUserAddress.setText(address.address_detail);
            addressId = address.address_id;
        }
    }

    @Override
    public void showSGDet(SGDet sgDet) {
        //set url
        ((SGDetActivity) getActivity()).setUrl(sgDet.contents);
        //set banner_my_style
        this.sgDet = sgDet;
        List<String> photos = sgDet.photos;
        if (photos == null || photos.size() == 0) {
            banner.setVisibility(View.GONE);
        } else {
            banner.setVisibility(View.VISIBLE);
            banner.setIndicatorGravity(BannerConfig.RIGHT);
            banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
            banner.setImageLoader(new PJImageLoader());
            banner.setImages(sgDet.photos);
            banner.start();
        }
        //set score
        String score = sgDet.integral;
        String scoTrs = " 积分";
        SpannableStringBuilder ss = new SpannableStringBuilder(score + scoTrs);
        ss.setSpan(new RelativeSizeSpan(0.65f), score.length(), ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(0xff999999), score.length(), ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvScore.setText(ss);
        //set total score
        ((SGDetActivity) getActivity()).setScore(score);

        tvName.setText(sgDet.title);
        tvLeftCount.setText("库存: " + sgDet.stock);
        tvExpressFee.setText("快递: " + sgDet.express);
        //set stock
        numberBtn.setMaxNumber(sgDet.stock);
        if (type == 1) {//线上
            latBDet.setVisibility(View.GONE);
            Address address = sgDet.receiving_address;
            if (address == null) {//无地址
                latNoAdd.setVisibility(View.VISIBLE);
                latHasAdd.setVisibility(View.GONE);
            } else {//有地址
                if (TextUtils.isEmpty(address.consignee)) {
                    latNoAdd.setVisibility(View.VISIBLE);
                    latHasAdd.setVisibility(View.GONE);
                } else {
                    latNoAdd.setVisibility(View.GONE);
                    latHasAdd.setVisibility(View.VISIBLE);
                    tvUserName.setText(address.consignee);
                    tvUserMobile.setText(address.mobile);
                    tvUserAddress.setText(address.address);
                    addressId = address.address_id;
                }
            }
        } else {//线下
            latBDet.setVisibility(View.VISIBLE);
            GlideArms.with(this).load(sgDet.shop_doorhead).into(imBLogo);
            tvBName.setText(sgDet.shop_name);
        }
        if (!TextUtils.isEmpty(sgDet.considerations)) {
            tvAttention.setText(sgDet.considerations);
        } else {
            tvAttention.setText("无注意事项");
        }
    }

    @Override
    public void clickAdd(int num) {
        this.currentNum = num;
        setTotalScore(num);

    }

    @Override
    public void clickMinus(int num) {
        this.currentNum = num;
        setTotalScore(num);
    }

    private void setTotalScore(int num) {
        String unitScore = sgDet.integral;
        try {
            Double unitScoreInt = Double.parseDouble(unitScore);
            ((SGDetActivity) getActivity()).setScore(NumberFormatUtils.getNewDouble(num * unitScoreInt));
        } catch (Exception e) {
            ((SGDetActivity) getActivity()).setScore(unitScore);
        }
    }

    public void exchange() {
        if (!AppConfig.getInstance().getBoolean(ConfigTag.IS_LOGIN, false)) {
            ArmsUtils.startActivity(LoginActivity.class);
            return;
        }
        if (type == 1 && addressId == 0) {
            showToast("请选择收获地址！");
            return;
        }
        String unitScore = sgDet.integral;
        Double unitScoreInt = Double.parseDouble(unitScore);
        String totalScore = NumberFormatUtils.getNewDouble(currentNum * unitScoreInt) + " 积分";
        DialogUtils.createSureScoreDialog(getContext(), totalScore, new DetDialogCallback() {
            @Override
            public void handleLeft(Dialog dialog) {
                mPresenter.exchange(type, goodsId, currentNum, addressId);
            }
        });
    }

    @Override
    public void showOrderId(int orderId) {
        getActivity().finish();
        ExSucActivity.start(getActivity(), orderId, type, 0, goodsId);
    }
}
