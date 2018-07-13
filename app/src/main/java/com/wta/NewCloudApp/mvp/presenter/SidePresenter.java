package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.ArmsUtils;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.mvp.contract.SideContract;
import com.wta.NewCloudApp.mvp.model.BusinessModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.view.BaseDataView;

import java.util.List;

import javax.inject.Inject;


@FragmentScope
public class SidePresenter extends BBasePresenter<BusinessModel, SideContract.View> {
    @Inject
    public SidePresenter(BusinessModel model, SideContract.View rootView) {
        super(model, rootView);
    }

    public void getData(boolean isRefresh) {
        doRequest(buildListRequest(mModel.getBusiness(isRefresh)),1);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what==1) {
            if (mRootView instanceof BaseDataView){
                ((BaseDataView) mRootView).getData(what, (List) result.data);
            }else {
                ArmsUtils.makeText(App.getInstance(),"view is not implements baseDataView");
            }
        }
    }
}
