package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.umeng.socialize.UMShareAPI;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.TabWhat;
import com.wta.NewCloudApp.mvp.ui.fragment.HomeFragment;
import com.wta.NewCloudApp.mvp.ui.fragment.MineFragment;
import com.wta.NewCloudApp.mvp.ui.fragment.SideFragment;
import com.wta.NewCloudApp.mvp.ui.widget.tabLayout.CommonTabLayout2;
import com.wta.NewCloudApp.uitls.ConfigTag;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;


public class MainActivity extends BaseLoadingActivity {

    @BindView(R.id.tab_layout)
    CommonTabLayout2 tabLayout;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        tabLayout.setTabData(createTabData(), this, R.id.frameLayout, createFragments());
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position == 2) {
                    if (!AppConfig.getInstance().getBoolean(ConfigTag.IS_LOGIN, false))
                        ArmsUtils.startActivity(LoginActivity.class);
                    else tabLayout.setCurrentTab(position);
                } else tabLayout.setCurrentTab(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) bind.unbind();
        UMShareAPI.get(this).release();
    }

    private ArrayList<Fragment> createFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>(3);
        fragments.add(new HomeFragment());
        fragments.add(new SideFragment());
        fragments.add(new MineFragment());
        return fragments;
    }

    private ArrayList<CustomTabEntity> createTabData() {
        ArrayList<CustomTabEntity> customTabEntities = new ArrayList<>(3);
        customTabEntities.add(new CustomTabEntity() {
            @Override
            public String getTabTitle() {
                return "首页";
            }

            @Override
            public int getTabSelectedIcon() {
                return R.mipmap.tab_home_select;
            }

            @Override
            public int getTabUnselectedIcon() {
                return R.mipmap.tab_home_unselect;
            }
        });
        customTabEntities.add(new CustomTabEntity() {
            @Override
            public String getTabTitle() {
                return "周边";
            }

            @Override
            public int getTabSelectedIcon() {
                return R.mipmap.tab_side_select;
            }

            @Override
            public int getTabUnselectedIcon() {
                return R.mipmap.tab_side_unselect;
            }
        });
        customTabEntities.add(new CustomTabEntity() {
            @Override
            public String getTabTitle() {
                return "我的";
            }

            @Override
            public int getTabSelectedIcon() {
                return R.mipmap.tab_mine_select;
            }

            @Override
            public int getTabUnselectedIcon() {
                return R.mipmap.tab_mine_unselect;
            }
        });
        return customTabEntities;
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

    @Subscriber
    public void onLoginSuccess(TabWhat no) {
        Timber.d("onLoginSuccess: %s", no.position);
        tabLayout.setCurrentTab(no.position);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public int setUIMode() {
        return UIMODE_TRANSPARENT_NOTALL;
    }

    @Override
    public boolean useFragment() {
        return true;
    }
}
