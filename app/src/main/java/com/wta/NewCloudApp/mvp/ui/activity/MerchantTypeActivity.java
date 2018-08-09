package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerMerchantTypeComponent;
import com.wta.NewCloudApp.di.module.MerchantTypeModule;
import com.wta.NewCloudApp.mvp.contract.MerchantTypeContract;
import com.wta.NewCloudApp.mvp.model.entity.BType;
import com.wta.NewCloudApp.mvp.presenter.MerchantTypePresenter;
import com.wta.NewCloudApp.mvp.ui.widget.tag.TagAdapter;
import com.wta.NewCloudApp.mvp.ui.widget.tag.TagFlowLayout;
import com.wta.NewCloudApp.uitls.FinalUtils;
import com.zhy.view.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MerchantTypeActivity extends BaseLoadingActivity<MerchantTypePresenter> implements MerchantTypeContract.View {
    @BindView(R.id.flowLayout)
    TagFlowLayout flowLayout;
    private List<BType> types = new ArrayList<>();
    private String beforeTag = "休闲";
    private TagAdapter<BType> tagAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMerchantTypeComponent
                .builder()
                .appComponent(appComponent)
                .merchantTypeModule(new MerchantTypeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_merchant_type;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        beforeTag = getIntent().getStringExtra("tag");
        mPresenter.getTypes();
    }

    public static void startType(Activity context, String tag) {
        Intent intent = new Intent(context, MerchantTypeActivity.class);
        intent.putExtra("tag", tag);
        context.startActivityForResult(intent, FinalUtils.REQUEST_TAG);
    }

    @Override
    public void showBTypeList(List<BType> types) {
        if (types != null && types.size() > 0) {
            this.types.clear();
            this.types.addAll(types);
            if (tagAdapter == null) {
                tagAdapter = new TagAdapter<BType>(this.types) {
                    @Override
                    public View getView(FlowLayout parent, int position, BType bType) {
                        TextView textView = (TextView) getLayoutInflater().inflate(R.layout.b_type_item, parent, false);
                        textView.setText(bType.type_name);
                        return textView;
                    }

                    @Override
                    public boolean setSelected(int position, BType bType) {
                        return bType.type_name.equals(beforeTag);
                    }
                };
                flowLayout.setAdapter(tagAdapter);
                flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = getIntent();
                                intent.putExtra("tag",types.get(position));
                                setResult(RESULT_OK,intent);
                                finish();
                            }
                        },300);
                        return true;
                    }
                });
            }else {
                tagAdapter.notifyDataChanged();
            }
        }
    }
}
