package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.wta.NewCloudApp.mvp.contract.ScoreGoodsListContract;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.SG2;
import com.wta.NewCloudApp.mvp.model.entity.ScoreGoods;
import com.wta.NewCloudApp.mvp.ui.fragment.ScoreGoodsListFragment;
import com.wta.NewCloudApp.mvp.view.BaseDataView;

import java.util.List;


@FragmentScope
public class ScoreGoodsListPresenter extends BBasePresenter<ScoreGoodsListContract.Model, ScoreGoodsListContract.View> {

    @Inject
    public ScoreGoodsListPresenter(ScoreGoodsListContract.Model model, ScoreGoodsListContract.View rootView) {
        super(model, rootView);
    }

    public void getScoreGoods(int position, int type, String keywords, Double lat, Double lon, boolean isRefresh) {
        doRequest(buildListRequest(mModel.getScoreGoods(position, type, keywords, lat, lon, isRefresh)), 1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            mRootView.getMulti(what, ((SG2) result.data));
        }
    }

    @Override
    public void handleException(int what, Throwable t) {
        super.handleException(what, t);
        if (what == 1) {
            mRootView.loadFailed();
        }
    }

    @Override
    protected boolean isActivity() {
        return false;
    }
}
