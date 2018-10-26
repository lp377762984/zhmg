package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerExRecDetComponent;
import com.wta.NewCloudApp.di.module.ExRecDetModule;
import com.wta.NewCloudApp.mvp.contract.ExRecDetContract;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.SGDet;
import com.wta.NewCloudApp.mvp.presenter.ExRecDetPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 兑换详情
 */
public class ExRecDetActivity extends BaseLoadingActivity<ExRecDetPresenter> implements ExRecDetContract.View {

    @BindView(R.id.tv_status_top)
    TextView tvStatusTop;
    @BindView(R.id.im_status)
    ImageView imStatus;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_mobile)
    TextView tvUserMobile;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.lat_address)
    ConstraintLayout latAddress;
    @BindView(R.id.im_b_logo)
    RoundedImageView imBLogo;
    @BindView(R.id.tv_b_name)
    TextView tvBName;
    @BindView(R.id.tv_b_loc)
    TextView tvBLoc;
    @BindView(R.id.lat_business)
    ConstraintLayout latBusiness;
    @BindView(R.id.im_goods)
    ImageView imGoods;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_complete_time)
    TextView tvCompleteTime;
    @BindView(R.id.tv_status_btm)
    TextView tvStatusBtm;
    @BindView(R.id.tv_order_no)
    TextView tvOrderNo;
    @BindView(R.id.btn_btm)
    TextView btnBtm;
    @BindView(R.id.tv_express)
    TextView tvExpress;

    private SGDet sgDet;
    private int type;
    private int status;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerExRecDetComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .exRecDetModule(new ExRecDetModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_ex_rec_det; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    public static void start(Activity activity, int orderId, int type, int status) {
        Intent intent = new Intent(activity, ExRecDetActivity.class);
        intent.putExtra("order_id", orderId);
        intent.putExtra("type", type);
        intent.putExtra("status", status);
        activity.startActivity(intent);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type", -1);
        status = getIntent().getIntExtra("status", -1);
        mPresenter.getExRecDet(getIntent().getIntExtra("order_id", -1));
    }

    @OnClick({R.id.im_b_logo, R.id.tv_b_name, R.id.tv_b_loc, R.id.btn_btm, R.id.tv_more_gift})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_b_logo:
                SideDetActivity.startDet(this, sgDet.store_id, 1);
                break;
            case R.id.tv_b_name:
                SideDetActivity.startDet(this, sgDet.store_id, 1);
                break;
            case R.id.tv_b_loc:
                SideDetActivity.startDet(this, sgDet.store_id, 1);
                break;
            case R.id.btn_btm:
                if (status == 0) {//待收货
                    mPresenter.confirmGetGoods(sgDet.order_id);
                } else {//再次兑换
                    SGDetActivity.start(this, sgDet.goods_id, type);
                }
                break;
            case R.id.tv_more_gift:
                StoreGoodsListActivity.start(this, sgDet.store_id);
                break;
        }
    }

    @Override
    public void ExRecDet(SGDet sgDet) {
        this.sgDet = sgDet;
        tvStatusTop.setText(status == 0 ? "待收货" : "已完成");
        imStatus.setImageResource(status == 0 ? R.mipmap.deliver : R.mipmap.complete);
        if (type == 1) {//线上
            latAddress.setVisibility(View.VISIBLE);
            latBusiness.setVisibility(View.GONE);
            tvUserName.setText(sgDet.consignee);
            tvUserMobile.setText(sgDet.mobile);
            tvAddress.setText(sgDet.address);
        } else {
            latAddress.setVisibility(View.GONE);
            latBusiness.setVisibility(View.VISIBLE);
            GlideArms.with(this).load(sgDet.shop_doorhead).into(imBLogo);
            tvBLoc.setText(sgDet.store_address);
            tvBName.setText(sgDet.store_name);
        }
        GlideArms.with(this).load(sgDet.goods_img).placeholder(R.mipmap.side_b_placeholder).into(imGoods);
        //set unit score
        String uScore = sgDet.unit_integral;
        String append = " 积分";
        SpannableString ss = new SpannableString(uScore + append);
        ss.setSpan(new ForegroundColorSpan(0xff333333), uScore.length(), ss.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tvScore.setText(ss);
        tvName.setText(sgDet.goods_name);
        tvExpress.setText(sgDet.express);
        int num = sgDet.num;
        String s1 = "共计" + num + "件商品 合计:";
        String s2 = sgDet.integral;
        String s3 = "积分";
        SpannableString ss1 = new SpannableString(s1 + s2 + s3);
        ss1.setSpan(new ForegroundColorSpan(0xffff4c6a), s1.length(), (s1 + s2).length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        ss1.setSpan(new RelativeSizeSpan(1.27f), s1.length(), (s1 + s2).length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        ss1.setSpan(new ForegroundColorSpan(0xffff4c6a), (s1 + s2).length(), ss1.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tvTotal.setText(ss1);
        tvOrderNo.setText(sgDet.ordersn);
        tvStatusBtm.setText(status == 0 ? "待收货" : "已完成");
        tvOrderTime.setText(sgDet.create_time);
        tvCompleteTime.setText(sgDet.update_time);

        btnBtm.setVisibility(type == 2 && status == 0 ? View.GONE : View.VISIBLE);
        btnBtm.setText(status == 0 ? "确认收货" : "再次兑换");
    }

    @Override
    public void confirmSuccess(Result result) {
        showToast(result.msg);
        finish();
    }
}
