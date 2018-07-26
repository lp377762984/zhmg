package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.AddAddressContract;
import com.wta.NewCloudApp.mvp.model.UserModel;
import com.wta.NewCloudApp.mvp.model.entity.Address;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import javax.inject.Inject;


@ActivityScope
public class AddAddressPresenter extends BBasePresenter<UserModel, AddAddressContract.View> {


    @Inject
    public AddAddressPresenter(UserModel model, AddAddressContract.View rootView) {
        super(model, rootView);
    }

    public void saveAddress(String name, String mobile, int province, int city, int district, String address, int isDefault) {
        doRequest(buildRequest(mModel.addAddress(name, mobile, province, city, district, address, isDefault)), 1);
    }

    public void editAddress(int id, String name, String mobile, int province, int city, int district, String address, int isDefault) {
        doRequest(buildRequest(mModel.editAddress(id, name, mobile, province, city, district, address, isDefault)), 2);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        mRootView.showAddress((Address) result.data);
    }
}
