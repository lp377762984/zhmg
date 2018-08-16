package com.wta.NewCloudApp.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.di.component.DaggerScoreListComponent;
import com.wta.NewCloudApp.di.module.ScoreListModule;
import com.wta.NewCloudApp.mvp.contract.ScoreListContract;
import com.wta.NewCloudApp.mvp.model.entity.Score;
import com.wta.NewCloudApp.mvp.presenter.ScoreListPresenter;
import com.wta.NewCloudApp.mvp.ui.adapter.ScoreAdapter;


public class ScoreListFragment extends BaseListFragment<ScoreListPresenter> implements ScoreListContract.View {

    private int type;

    public static ScoreListFragment getInstance(int type) {
        ScoreListFragment fragment = new ScoreListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
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
        super.initData(savedInstanceState);
        type = getArguments().getInt("type", 0);
        for (int i = 0; i < 5; i++) {
            data.add(new Score("","墙头草","08.25 21:35","2500"));
        }
        View headView = getLayoutInflater().inflate(R.layout.score_item_head, recyclerView, false);
        adapter.addHeaderView(headView);
    }

    @Override
    protected void getAdapter() {
        adapter = new ScoreAdapter(R.layout.score_item, data);
    }

    @Override
    public void loadData(boolean isRefresh) {

    }
}
