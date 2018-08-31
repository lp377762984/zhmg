package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.mvp.contract.MsgContract;
import com.wta.NewCloudApp.mvp.model.UserModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.view.BaseDataView;

import java.util.List;

import javax.inject.Inject;


@ActivityScope
public class MsgPresenter extends BBasePresenter<UserModel, MsgContract.View> {

    @Inject
    public MsgPresenter(UserModel model, MsgContract.View rootView) {
        super(model, rootView);
    }

    public void getData(boolean isRefresh) {
        doRequest(buildListRequest(mModel.getMsgList(isRefresh)), 1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what==1) {
            if (mRootView instanceof BaseDataView){
                ((BaseDataView) mRootView).getData(what, (Result<List>) result);
            }else {
                ArmsUtils.makeText(App.getInstance(),"view is not implements baseDataView");
            }
        }
    }
}
