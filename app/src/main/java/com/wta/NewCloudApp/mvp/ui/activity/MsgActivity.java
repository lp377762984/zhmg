package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.di.component.DaggerMsgComponent;
import com.wta.NewCloudApp.di.module.MsgModule;
import com.wta.NewCloudApp.jiuwei210278.R;
import com.wta.NewCloudApp.mvp.contract.MsgContract;
import com.wta.NewCloudApp.mvp.model.entity.Msg;
import com.wta.NewCloudApp.mvp.presenter.MsgPresenter;
import com.wta.NewCloudApp.mvp.ui.adapter.MsgAdapter;
import com.wta.NewCloudApp.mvp.ui.adapter.SideAdapter;


public class MsgActivity extends BaseListActivity<MsgPresenter> implements MsgContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMsgComponent
                .builder()
                .appComponent(appComponent)
                .msgModule(new MsgModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_msg;
    }

    @Override
    protected void getAdapter() {
        adapter = new MsgAdapter(R.layout.msg_item, data);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Msg msg = (Msg) data.get(position);
                WebViewActivity.start(MsgActivity.this, msg.title, msg.get_url);
            }
        });
    }

    @Override
    public void loadData(boolean isRefresh) {
        mPresenter.getData(isRefresh);
    }
}
