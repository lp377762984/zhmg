package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.ui.fragment.SGDetBtmFragment;
import com.wta.NewCloudApp.mvp.ui.fragment.SGDetFragment;
import com.wta.NewCloudApp.mvp.ui.widget.vertical_drag.DragLayout;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * 礼品详情
 */
public class SGDetActivity extends BaseLoadingActivity {

    private int goodsId;
    private int type;
    private String url;
    @BindView(R.id.lat_title)
    public FrameLayout latTitle;
    @BindView(R.id.bottom)
    public FrameLayout latBtm;
    @BindView(R.id.tv_total_score)
    TextView tvTotalScore;
    SGDetFragment topFragment;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sgdet;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        goodsId = getIntent().getIntExtra("goods_id", -1);
        type = getIntent().getIntExtra("type", -1);
        topFragment = SGDetFragment.getInstance(goodsId, type);
        final SGDetBtmFragment btmFragment = new SGDetBtmFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.top, topFragment).add(R.id.bottom, btmFragment)
                .commit();

        DragLayout.ShowNextPageNotifier notifier = new DragLayout.ShowNextPageNotifier() {
            @Override
            public void onDragNext() {
                btmFragment.initView("http://www.baidu.com");
            }

            @Override
            public void onDragTop() {

            }
        };
        DragLayout draglayout = (DragLayout) findViewById(R.id.dragLayout);
        draglayout.setNextPageListener(notifier);
    }

    public static void start(Activity activity, int goodId, int type) {
        Intent intent = new Intent(activity, SGDetActivity.class);
        intent.putExtra("goods_id", goodId);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    @Override
    public boolean useFragment() {
        return true;
    }

    @Override
    public int setUIMode() {
        return UIMODE_TRANSPARENT_NOTALL;
    }

    @OnClick({R.id.im_back, R.id.tv_exchange})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                finish();
                break;
            case R.id.tv_exchange:
                topFragment.exchange();
                break;
        }
    }

    public void setScore(String score) {
        String str01 = "合计：";
        String str02 = score + "积分";
        SpannableString ss = new SpannableString(str01 + str02);
        ss.setSpan(new ForegroundColorSpan(0xffff4c6a), str01.length(), ss.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tvTotalScore.setText(ss);
    }
}
