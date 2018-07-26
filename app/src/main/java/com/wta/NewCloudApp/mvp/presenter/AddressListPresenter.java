package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.wta.NewCloudApp.mvp.contract.AddressListContract;
import com.wta.NewCloudApp.mvp.model.UserModel;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.view.BaseDataView;

import java.util.List;

import javax.inject.Inject;


@ActivityScope
public class AddressListPresenter extends BBasePresenter<UserModel, AddressListContract.View> {

    @Inject
    public AddressListPresenter(UserModel model, AddressListContract.View rootView) {
        super(model, rootView);
    }

    public void getAddressList() {
        doRequest(buildListRequest(mModel.getAddressList()), 1);
    }

    public void setDefault(int id) {
        doRequest(buildRequest(mModel.setDefaultAddress(id)), 2);
    }

    public void delAddress(int id) {
        doRequest(buildRequest(mModel.delAddress(id)), 3);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            ((BaseDataView) mRootView).getData(what, (List) result.data);
        } else if (what == 2) {
            mRootView.setDefault();
        } else if (what == 3) {
            mRootView.deleteSuccess();
        }
    }
}
