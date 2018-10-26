package com.wta.NewCloudApp.mvp.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerSGDetBtmComponent;
import com.wta.NewCloudApp.di.module.SGDetBtmModule;
import com.wta.NewCloudApp.mvp.contract.SGDetBtmContract;
import com.wta.NewCloudApp.mvp.presenter.SGDetBtmPresenter;
import com.wta.NewCloudApp.mvp.ui.widget.vertical_drag.CustWebView;

import butterknife.BindView;


public class SGDetBtmFragment extends BaseLoadingFragment<SGDetBtmPresenter> implements SGDetBtmContract.View {
    @BindView(R.id.webView)
    CustWebView webView;
    private boolean hasInited = false;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSGDetBtmComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .sGDetBtmModule(new SGDetBtmModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sgdet_btm, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }
        });
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
    }

    public void initView(String url) {
        if (null != webView && !hasInited && url != null) {
            hasInited = true;
            webView.loadUrl(url);
        }
    }
}
