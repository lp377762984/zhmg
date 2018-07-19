package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.di.component.DaggerSettingComponent;
import com.wta.NewCloudApp.di.module.SettingModule;
import com.wta.NewCloudApp.jiuwei210278.R;
import com.wta.NewCloudApp.mvp.contract.SettingContract;
import com.wta.NewCloudApp.mvp.model.entity.TabWhat;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.mvp.presenter.SettingPresenter;
import com.wta.NewCloudApp.uitls.ConfigTag;
import com.wta.NewCloudApp.uitls.DataCleanUtils;
import com.wta.NewCloudApp.uitls.FinalUtils;
import com.wta.NewCloudApp.uitls.PackageUtils;

import org.simple.eventbus.EventBus;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


public class SettingActivity extends BaseLoadingActivity<SettingPresenter> implements SettingContract.View, UMAuthListener {

    @BindView(R.id.lat_phone)
    RelativeLayout latPhone;
    @BindView(R.id.lat_wx)
    RelativeLayout latWx;
    @BindView(R.id.tv_memory)
    TextView tvMemory;
    @BindView(R.id.lat_clear)
    RelativeLayout latClear;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.lat_update)
    RelativeLayout latUpdate;
    @BindView(R.id.btn_exit)
    Button btnExit;
    @BindView(R.id.tv_phone_state)
    TextView tvPhoneState;
    @BindView(R.id.tv_wx_state)
    TextView tvWxState;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSettingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .settingModule(new SettingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_setting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvVersion.setText(PackageUtils.getPackageVersion(this));
        tvMemory.setText(DataCleanUtils.getApplicationDataSize(this));
        if (AppConfig.getInstance().getInt(ConfigTag.IS_MOBILE, 0) == 0) {
            tvPhoneState.setText("未绑定");
        } else {
            tvPhoneState.setText(AppConfig.getInstance().getString(ConfigTag.MOBILE, null));
            latPhone.setEnabled(false);
        }
        if (AppConfig.getInstance().getInt(ConfigTag.IS_WEIXIN, 0) == 0) {
            tvWxState.setText("未绑定");
        } else {
            tvWxState.setText(AppConfig.getInstance().getString(ConfigTag.WX_NAME, null));
            latWx.setEnabled(false);
        }

    }

    @OnClick({R.id.lat_phone, R.id.lat_wx, R.id.lat_clear, R.id.lat_update, R.id.btn_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lat_phone:
                BindPhoneActivity.startBind(this);
                break;
            case R.id.lat_wx:
                if (UMShareAPI.get(this).isInstall(this, SHARE_MEDIA.WEIXIN))
                    UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, this);
                else ArmsUtils.makeText(App.getInstance(), "您没有安装微信");
                break;
            case R.id.lat_clear:
                if (tvMemory.getText().equals("0K")) {
                    ArmsUtils.makeText(App.getInstance(), "无缓存");
                    return;
                }
                new AlertDialog.Builder(this)
                        .setTitle("提醒")
                        .setMessage("是否清除缓存")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                DataCleanUtils.cleanApplicationData(SettingActivity.this);
                                tvMemory.setText(DataCleanUtils.getApplicationDataSize(SettingActivity.this));
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

                break;
            case R.id.lat_update:
                break;
            case R.id.btn_exit:
                new AlertDialog.Builder(this)
                        .setTitle("提醒")
                        .setMessage("确定要退出登陆吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                AppConfig.getInstance().clearUser();
                                AppConfig.getInstance().removeValue("is_login");
                                EventBus.getDefault().post(new TabWhat(0));
                                finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == FinalUtils.REQUEST_BIND) {
            tvPhoneState.setText(AppConfig.getInstance().getString(ConfigTag.MOBILE, null));
        }
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        //showLoading();
    }

    @Override
    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
        //hideLoading();
        mPresenter.bindWX(map);
    }

    @Override
    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
        //hideLoading();
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media, int i) {
        ArmsUtils.makeText(this, "微信登陆已取消");
    }

    @Override
    public void showData(User user) {
        tvWxState.setText(user.wx_name);
        AppConfig.getInstance().putInt(ConfigTag.IS_WEIXIN, 1);
        AppConfig.getInstance().putString(ConfigTag.NICKNAME, (user.nickname));
    }
}
