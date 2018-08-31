package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.ScoreListContract;
import com.wta.NewCloudApp.mvp.model.IUserModel;
import com.wta.NewCloudApp.mvp.model.entity.BillType;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.ui.activity.ScoreListActivity;
import com.wta.NewCloudApp.mvp.view.BaseDataView;

import java.util.List;

import javax.inject.Inject;


@ActivityScope
public class ScoreListPresenter extends BBasePresenter<IUserModel, ScoreListContract.View> {

    @Inject
    public ScoreListPresenter(IUserModel model, ScoreListContract.View rootView) {
        super(model, rootView);
    }

    public void getBillsType() {
        doRequest(buildRequest(mModel.getBillsType()), 1);
    }

    public void getBillsList(boolean isRefresh,String status) {
        doRequest(buildListRequest(mModel.getBillsList(isRefresh,status)), 2);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            mRootView.showBillsType(((List<BillType>) result.data));
        } else if (what == 2) {
            ((BaseDataView) mRootView).getData(what, (Result<List>) result);
        }

    }

    @Override
    public <T> void handle404(int what, Result<T> result) {
        super.handle404(what, result);
        if (what == 1) {
            mRootView.getMContext().finish();
        }
    }
}
