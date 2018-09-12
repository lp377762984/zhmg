package com.wta.NewCloudApp.mvp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerScoreListComponent;
import com.wta.NewCloudApp.di.module.ScoreListModule;
import com.wta.NewCloudApp.mvp.contract.ScoreListContract;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.model.entity.BillType;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.Score;
import com.wta.NewCloudApp.mvp.presenter.ScoreListPresenter;
import com.wta.NewCloudApp.mvp.ui.activity.BScoreDetActivity;
import com.wta.NewCloudApp.mvp.ui.activity.CashDetActivity;
import com.wta.NewCloudApp.mvp.ui.activity.RScoreDetActivity;
import com.wta.NewCloudApp.mvp.ui.activity.UScoreDetActivity;
import com.wta.NewCloudApp.mvp.ui.adapter.ScoreAdapter;
import com.wta.NewCloudApp.uitls.DialogUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 积分账单
 */
public class ScoreListFragment extends BaseListFragment<ScoreListPresenter> implements ScoreListContract.View {

    private BillType billType;
    @BindView(R.id.lat_head)
    View latHead;
    @BindView(R.id.tv_total_score)
    TextView tvScore;
    @BindView(R.id.tv_date)
    TextView tvDate;
    private String date;

    public static ScoreListFragment getInstance(BillType billType) {
        ScoreListFragment fragment = new ScoreListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("billType", billType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerScoreListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .scoreListModule(new ScoreListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_score_list, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        date = formateTime(new Date(), "yyyy-MM");
        tvDate.setText(date);
        billType = (BillType) getArguments().getSerializable("billType");//放到super之前
        super.initData(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

    }

    @Override
    protected void getAdapter() {
        adapter = new ScoreAdapter(R.layout.score_item, data);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bill bill = (Bill) data.get(position);
                String status = bill.status;
                switch (status) {
                    case "saleStatus":
                        BScoreDetActivity.startDet(getActivity(), bill.bill_id);
                        break;
                    case "consumeStatus":
                        UScoreDetActivity.startDet(getActivity(), bill.bill_id);
                        break;
                    case "recommendStatus":
                        RScoreDetActivity.startDet(getActivity(), bill.bill_id);
                        break;
                }
            }
        });
    }

    @Override
    public void loadData(boolean isRefresh) {
        getData(isRefresh);
    }

    private void getData(boolean isRefresh) {
        mPresenter.getBillsList(isRefresh, billType.status, "1", "month", date);
    }

    @OnClick(R.id.im_calendar)
    public void onClickView(View view) {
        DialogUtils.showMonthTimePicker(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                isRefresh = true;
                ScoreListFragment.this.date = formateTime(date, "yyyy-MM");
                tvDate.setText(ScoreListFragment.this.date);
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
                tvScore.setText(result.white_score );
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

    @Override
    public void showBillsType(List<BillType> billType) {

    }

    @Override
    public Activity getMContext() {
        return null;
    }

    private String formateTime(Date date, String format) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(date);
    }
}
