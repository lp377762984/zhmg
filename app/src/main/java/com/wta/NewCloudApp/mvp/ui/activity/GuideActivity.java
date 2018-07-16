package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.jiuwei210278.R;
import com.wta.NewCloudApp.mvp.ui.widget.Banner2;
import com.wta.NewCloudApp.mvp.ui.widget.PJImageLoader;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class GuideActivity extends BaseActivity implements OnBannerListener {

    @BindView(R.id.banner)
    Banner2 banner;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        bind = ButterKnife.bind(this);
        List<Integer> list = Arrays.asList(R.mipmap.guide_01, R.mipmap.guide_02, R.mipmap.guide_03);
        banner.setNeedTurn(false);//设置禁止循环
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);//设置没有指示器
        banner.setImageLoader(new PJImageLoader());
        banner.setImages(list);
        banner.start();
        banner.setOnBannerListener(this);
    }

    @Override
    public void OnBannerClick(int position) {
        Timber.d("OnBannerClick: %s",position);
        if (position==2) {
            ArmsUtils.startActivity(MainActivity.class);
            finish();
            AppConfig.getInstance().putBoolean("need_guide",false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind!=null) bind.unbind();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
