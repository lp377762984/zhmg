package com.wta.NewCloudApp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;

import com.wta.NewCloudApp.di.component.DaggerBGroupListComponent;
import com.wta.NewCloudApp.di.module.BGroupListModule;
import com.wta.NewCloudApp.mvp.contract.BGroupListContract;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.model.entity.BillType;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.Score;
import com.wta.NewCloudApp.mvp.presenter.BGroupListPresenter;

import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.ui.adapter.GroupGetAdapter;
import com.wta.NewCloudApp.mvp.ui.adapter.ScoreAdapter;
import com.wta.NewCloudApp.mvp.ui.fragment.ScoreListFragment;
import com.wta.NewCloudApp.uitls.DialogUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 团队收益列表
 */
public class BGroupListActivity extends BaseListActivity<BGroupListPresenter> implements BGroupListContract.View {
    @BindView(R.id.lat_head)
    View latHead;
    @BindView(R.id.tv_total_score)
    TextView tvScore;
    @BindView(R.id.tv_date)
    TextView tvDate;
    private String date;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBGroupListComponent
                .builder()
                .appComponent(appComponent)
                .bGroupListModule(new BGroupListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bgroup_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        date = formateTime(new Date(), "yyyy-MM");
        tvDate.setText(date);
        super.initData(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    @Override
    protected void getAdapter() {
        adapter = new ScoreAdapter(R.layout.group_get_item, data);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bill bill = (Bill) data.get(position);
                RScoreDetActivity.startDet(BGroupListActivity.this, bill.bill_id);
            }
        });
    }

    @Override
    public void loadData(boolean isRefresh) {
        getData(isRefresh);
    }

    @OnClick(R.id.im_calendar)
    public void onClickView(View view) {
        DialogUtils.showMonthTimePicker(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                isRefresh = true;
                BGroupListActivity.this.date = formateTime(date, "yyyy-MM");
                tvDate.setText(BGroupListActivity.this.date);
                getData(isRefresh);
            }
        }).show();
    }

    @Override
    public void getData(int what, Result<List> result) {
        List msgs = result.data;
        if (!isRefresh && (msgs == null || msgs.size() == 0))
            isComplete = true;
        if (isRefresh) {
            data.clear();
            if (msgs != null) {
                data.addAll(msgs);
                //latHead.setVisibility(View.VISIBLE);
                tvScore.setText(result.white_score + "");
                adapter.notifyDataSetChanged();
            } else {
                //latHead.setVisibility(View.GONE);
            }
        } else {
            int beforeSize = data.size();
            if (msgs != null) {
                data.addAll(msgs);
                adapter.notifyItemRangeInserted(beforeSize, msgs.size());
            }
        }
    }

    private void getData(boolean isRefresh) {
        mPresenter.getGBillsList(isRefresh, 1223, "month", date);
    }

    private String formateTime(Date date, String format) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(date);
    }
}
