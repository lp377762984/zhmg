package com.wta.NewCloudApp.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wta.NewCloudApp.BuildConfig;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.di.component.DaggerSettingComponent;
import com.wta.NewCloudApp.di.module.SettingModule;
import com.wta.NewCloudApp.mvp.contract.SettingContract;
import com.wta.NewCloudApp.mvp.model.WXUserInfo;
import com.wta.NewCloudApp.mvp.model.entity.TabWhat;
import com.wta.NewCloudApp.mvp.model.entity.Update;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.mvp.model.entity.WXAccessToken;
import com.wta.NewCloudApp.mvp.presenter.SettingPresenter;
import com.wta.NewCloudApp.mvp.ui.listener.DetDialogCallback;
import com.wta.NewCloudApp.uitls.ConfigTag;
import com.wta.NewCloudApp.uitls.DataCleanUtils;
import com.wta.NewCloudApp.uitls.DialogUtils;
import com.wta.NewCloudApp.uitls.FinalUtils;
import com.wta.NewCloudApp.uitls.PackageUtils;
import com.wta.NewCloudApp.wxapi.login_share.ThirdAuthManager;
import com.wta.NewCloudApp.wxapi.login_share.WXOpenIdListener;
import com.wta.NewCloudApp.wxapi.login_share.WXUserListener;

import org.simple.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;


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
    @BindView(R.id.tv_phone_state)
    TextView tvPhoneState;
    @BindView(R.id.tv_wx_state)
    TextView tvWxState;
    @BindView(R.id.tv_ali_state)
    TextView tvAliState;
    @BindView(R.id.lat_ali)
    RelativeLayout latAli;
    private ProgressDialog progressDialog;
    private Dialog updateDialog;

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
        if (AppConfig.getInstance().getInt(ConfigTag.IS_ALIPAY, 0) == 0) {
            tvAliState.setText("未绑定");
        } else {
            tvAliState.setText("已绑定");
            latAli.setEnabled(false);
        }

    }

    @OnClick({R.id.lat_phone, R.id.lat_wx, R.id.lat_clear, R.id.lat_update, R.id.btn_exit,R.id.lat_ali})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lat_phone:
                BindPhoneActivity.startBind(this);
                break;
            case R.id.lat_wx:
                if (!App.getInstance().getWXAPI().isWXAppInstalled()) {
                    ArmsUtils.makeText(App.getInstance(), "您没有安装微信");
                } else {
                    ThirdAuthManager.getInstance().requestAuth(this, new WXOpenIdListener() {
                        @Override
                        public void showWXOpenId(WXAccessToken wxAccessToken) {
                            ThirdAuthManager.getInstance().requestWXUserInfo(wxAccessToken.access_token, wxAccessToken.openid, new WXUserListener() {
                                @Override
                                public void showWXUser(WXUserInfo info) {
                                    mPresenter.bindWX(info);
                                }
                            });
                        }
                    });
                }
                break;
            case R.id.lat_clear:
                if (tvMemory.getText().equals("0K")) {
                    ArmsUtils.makeText(App.getInstance(), "无缓存");
                    return;
                }
                DialogUtils.showAlertDialog(this, "是否清除缓存？", new DetDialogCallback() {
                    @Override
                    public void handleRight(Dialog dialog) {
                        dialog.dismiss();
                        DataCleanUtils.cleanApplicationData(SettingActivity.this);
                        tvMemory.setText(DataCleanUtils.getApplicationDataSize(SettingActivity.this));
                    }
                }).show();
                break;
            case R.id.lat_update:
                mPresenter.checkUpdate();
                break;
            case R.id.btn_exit:
                DialogUtils.showAlertDialog(this, "确定要退出登陆吗？", new DetDialogCallback() {
                    @Override
                    public void handleRight(Dialog dialog) {
                        dialog.dismiss();
                        AppConfig.getInstance().clearUser();
                        AppConfig.getInstance().removeValue(ConfigTag.IS_LOGIN);
                        EventBus.getDefault().post(new TabWhat(0));
                        finish();
                    }
                }).show();
                break;
            case R.id.lat_ali:
                mPresenter.bindAli();
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
    public void showData(User user) {
        tvWxState.setText(user.wx_name);
        AppConfig.getInstance().putInt(ConfigTag.IS_WEIXIN, 1);
        AppConfig.getInstance().putString(ConfigTag.WX_NAME, (user.wx_name));
    }

    @SuppressLint("CheckResult")
    @Override
    public void showUpdate(Update update) {
        if (updateDialog == null) {
            updateDialog = DialogUtils.createUpdateDialog(this, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateDialog.dismiss();
                    new RxPermissions(SettingActivity.this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe(new Consumer<Boolean>() {
                                @Override
                                public void accept(Boolean aBoolean) throws Exception {
                                    if (aBoolean) {
                                        mPresenter.downLoadApp(update.version_stable_url);
                                    } else showToast("请打开读写权限");
                                }
                            });
                }
            });
        }
        updateDialog.show();
    }

    public void installApp(File file) {
        if (file == null || !file.exists()) {
            showToast("文件不存在");
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT > 23) {
            Uri uri = FileProvider.getUriForFile(SettingActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setDataAndType(uri, "application/vnd.android.package-archive");
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(i);
        } else {
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setDataAndType(Uri.parse("file://" + file.getAbsolutePath()), "application/vnd.android.package-archive");
            startActivity(i);
        }
    }

    @Override
    public void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        }
        progressDialog.show();
    }

    @Override
    public void updateProgress(int progress) {
        progressDialog.setProgress(progress);
        if (progress == 100) {
            progressDialog.dismiss();
            installApp(new File(Environment.getExternalStorageDirectory() + "/temp/zhmg.apk"));
        }
    }

    @Override
    public Activity getActivityCet() {
        return this;
    }

    @Override
    public void bindAliSuccess() {
        AppConfig.getInstance().putInt(ConfigTag.IS_ALIPAY,1);
        tvAliState.setText("已绑定");
        latAli.setEnabled(false);
    }

}
