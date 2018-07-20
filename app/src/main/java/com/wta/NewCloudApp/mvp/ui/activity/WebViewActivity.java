package com.wta.NewCloudApp.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.config.AppConfig;
import com.wta.NewCloudApp.jiuwei210278.R;
import com.wta.NewCloudApp.mvp.ui.widget.MoneyBar;
import com.wta.NewCloudApp.uitls.DialogUtils;
import com.wta.NewCloudApp.uitls.EncodeUtils;
import com.wta.NewCloudApp.uitls.PackageUtils;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class WebViewActivity extends BaseActivity {
    private String url;
    private WebView webView;
    private String name;
    private MoneyBar moneyBar;

    public static void start(Context context, String name, String url) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(url)) {
            Timber.tag("WebViewActivity").e("start: name为空或者url为空");
            return;
        }
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void getMsgFromBefore() {
        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra("name");
            moneyBar.setTextTitle(name);
            url = intent.getStringExtra("url");
            if (url != null) {
                Dialog waitDialog = DialogUtils.createWaitDialog(this);

                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        waitDialog.dismiss();
                    }

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        waitDialog.show();
                    }
                });
                webView.loadUrl(url, createHeader());
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initViews() {
        webView = ((WebView) findViewById(R.id.webView));
        webView.getSettings().setJavaScriptEnabled(true);

        moneyBar = ((MoneyBar) findViewById(R.id.mb));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (webView.canGoBack()) {
            webView.goBack();  //返回上一页
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {  //按下实体的返回键
            if (webView.canGoBack()) {  //说明WebView有上一页
                webView.goBack();  //WebView返回上一页
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_web_view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initViews();
        getMsgFromBefore();
    }

    private Map<String, String> createHeader() {
        Map<String, String> map = new HashMap<>();
        map.put("clientfrom", "android" + PackageUtils.getAndroidVersion());
        map.put("deviceuuid", PackageUtils.getDeviceId());
        String nonceStr = EncodeUtils.makeNonceStr();
        map.put("noncestr", nonceStr);
        map.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        map.put("sign", EncodeUtils.makeSign(nonceStr, url));
        map.put("sessionId", AppConfig.getInstance().getString("session_id", null));
        map.put("accessToken", AppConfig.getInstance().getString("access_token", null));
        return map;
    }
}
