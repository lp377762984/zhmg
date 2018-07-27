package com.wta.NewCloudApp.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.lifecycle.Lifecycleable;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.di.component.DaggerBindPhoneComponent;
import com.wta.NewCloudApp.di.module.BindPhoneModule;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.contract.BindPhoneContract;
import com.wta.NewCloudApp.mvp.model.entity.LoginEntity;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.TabWhat;
import com.wta.NewCloudApp.mvp.model.entity.User;
import com.wta.NewCloudApp.mvp.presenter.BindPhonePresenter;
import com.wta.NewCloudApp.mvp.ui.widget.EditTextHint;
import com.wta.NewCloudApp.uitls.ConfigTag;
import com.wta.NewCloudApp.uitls.FinalUtils;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class BindPhoneActivity extends BaseLoadingActivity<BindPhonePresenter> implements BindPhoneContract.View {

    @BindView(R.id.et_phone)
    EditTextHint etPhone;
    @BindView(R.id.et_code)
    EditTextHint etCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBindPhoneComponent
                .builder()
                .appComponent(appComponent)
                .bindPhoneModule(new BindPhoneModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bind_phone;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
    }

    public static void startBind(Activity activity) {
        Intent intent = new Intent(activity, BindPhoneActivity.class);
        activity.startActivityForResult(intent, FinalUtils.REQUEST_BIND);
    }

    @OnClick({R.id.tv_get_code, R.id.btn_apply})
    public void onViewClicked(View view) {
        String code = etCode.getText().toString();
        String phone = etPhone.getText().toString();
        switch (view.getId()) {
            case R.id.tv_get_code:
                if (TextUtils.isEmpty(phone)) {
                    showToast("请输入手机号码");
                    return;
                }
                mPresenter.sendCode(phone);
                break;
            case R.id.btn_apply:
                if (TextUtils.isEmpty(phone)) {
                    showToast("请输入手机号码");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    showToast("请输入验证码");
                    return;
                }
                Intent intent = getIntent();
                intent.putExtra("mobile",phone);
                intent.putExtra("code",code);
                setResult(RESULT_OK,getIntent());
                finish();
                //mPresenter.bindPhone(phone, code, userMap);
                break;
        }
    }

    @Override
    public void sendCode(Result<User> result) {
        ArmsUtils.makeText(getApplicationContext(), "短信验证码已发送");
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .compose(RxLifecycleUtils.bindToLifecycle((Lifecycleable) this))
                .subscribeOn(Schedulers.io())
                .take(30, TimeUnit.SECONDS)
                .doOnSubscribe(disposable -> tvGetCode.setEnabled(false))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(aLong -> 30 - aLong)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onNext(Long aLong) {
                        tvGetCode.setText(aLong + "s");
                    }

                    @Override
                    public void onError(Throwable e) {
                        tvGetCode.setEnabled(true);
                        tvGetCode.setText("获取验证码");
                    }

                    @Override
                    public void onComplete() {
                        tvGetCode.setEnabled(true);
                        tvGetCode.setText("获取验证码");
                    }
                });
    }

    @Override
    public void bindPhone(Result<LoginEntity> result) {
        showToast(result.msg);
        setResult(RESULT_OK);
        finish();
    }
}
