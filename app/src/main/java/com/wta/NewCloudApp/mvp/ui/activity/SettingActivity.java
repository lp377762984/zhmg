package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.di.component.DaggerSettingComponent;
import com.wta.NewCloudApp.di.module.SettingModule;
import com.wta.NewCloudApp.jiuwei210278.R;
import com.wta.NewCloudApp.mvp.contract.SettingContract;
import com.wta.NewCloudApp.mvp.model.entity.TabWhat;
import com.wta.NewCloudApp.mvp.presenter.SettingPresenter;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;


public class SettingActivity extends BaseLoadingActivity<SettingPresenter> implements SettingContract.View {

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

    }

    @OnClick({R.id.lat_phone, R.id.lat_wx, R.id.lat_clear, R.id.lat_update, R.id.btn_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lat_phone:
                break;
            case R.id.lat_wx:
                break;
            case R.id.lat_clear:
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
}
