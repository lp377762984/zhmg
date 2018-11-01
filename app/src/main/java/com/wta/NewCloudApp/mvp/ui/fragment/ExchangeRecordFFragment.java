package com.wta.NewCloudApp.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerExchangeRecordFComponent;
import com.wta.NewCloudApp.di.module.ExchangeRecordFModule;
import com.wta.NewCloudApp.mvp.contract.ExchangeRecordFContract;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.ScoreGoods;
import com.wta.NewCloudApp.mvp.presenter.ExchangeRecordFPresenter;
import com.wta.NewCloudApp.mvp.ui.activity.ExRecDetActivity;
import com.wta.NewCloudApp.mvp.ui.activity.SGDetActivity;
import com.wta.NewCloudApp.mvp.ui.activity.SideDetActivity;
import com.wta.NewCloudApp.mvp.ui.adapter.ExchangeRecordAdapter;

import timber.log.Timber;

/**
 * 兑换记录
 */
public class ExchangeRecordFFragment extends BaseListFragment<ExchangeRecordFPresenter> implements ExchangeRecordFContract.View {

    private int status = -3;
    private boolean isViewCreated;
    private boolean hasLoadData;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerExchangeRecordFComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .exchangeRecordFModule(new ExchangeRecordFModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exchange_record, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        status = getArguments().getInt("status");
        super.initData(savedInstanceState);
        if (!hasLoadData && getUserVisibleHint()) {
            loadData(isRefresh);
           // Timber.tag("OnlyMeKnown").i(status+" is loading data on create");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
    }

    @Override
    public void loadData(boolean isRefresh) {
        //Timber.tag("OnlyMeKnown").i(status+" is loading data, isViewCreated "+isViewCreated+", getVisible "+getUserVisibleHint());
        hasLoadData = true;
        mPresenter.getExchangeRec(status, isRefresh);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //Timber.tag("OnlyMeKnown").i(status + ",isVisibleToUser = " + isVisibleToUser /*+ ",isViewCreated = " + isViewCreated*/);
        if (isVisibleToUser && isViewCreated) {
            //Timber.tag("OnlyMeKnown").i(status+" is loading data on setUserVisibleHint");
            isRefresh = true;
            loadData(isRefresh);
        }

    }

    public static ExchangeRecordFFragment getInstance(int status) {
        ExchangeRecordFFragment fragment = new ExchangeRecordFFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void getAdapter() {
        adapter = new ExchangeRecordAdapter(R.layout.exchange_record_item, data);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ScoreGoods sg = (ScoreGoods) data.get(position);
                ExRecDetActivity.start(getActivity(), sg.order_id, sg.type, sg.status);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ScoreGoods scoreGoods = (ScoreGoods) data.get(position);
                switch (view.getId()) {
                    case R.id.btn_more:
                        if (scoreGoods.status == 0) {//确认收货
                            mPresenter.sureGetGift(scoreGoods.order_id);
                        } else {//再次兑换
                            SGDetActivity.start(getActivity(), scoreGoods.goods_id, scoreGoods.type);
                        }
                        break;
                    case R.id.tv_store_name:
                        SideDetActivity.startDet(getActivity(), scoreGoods.store_id);
                        break;
                    case R.id.im_store_logo:
                        SideDetActivity.startDet(getActivity(), scoreGoods.store_id);
                        break;
                }
            }
        });
    }

    @Override
    public void confirmSuccess(Result result) {
        showToast("确认收货成功");
        isRefresh = true;
        loadData(isRefresh);
    }

    @Override
    protected boolean autoRequest() {
        return false;
    }
}
