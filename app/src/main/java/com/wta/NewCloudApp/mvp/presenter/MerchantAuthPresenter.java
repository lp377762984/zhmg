package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.MerchantAuthContract;
import com.wta.NewCloudApp.mvp.model.IBusinessModel;
import com.wta.NewCloudApp.mvp.model.entity.AuthInfo;
import com.wta.NewCloudApp.mvp.model.entity.ErrorBusiness;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import javax.inject.Inject;


@ActivityScope
public class MerchantAuthPresenter extends BBasePresenter<IBusinessModel, MerchantAuthContract.View> {

    @Inject
    public MerchantAuthPresenter(IBusinessModel model, MerchantAuthContract.View rootView) {
        super(model, rootView);
    }

    public void applyAuth(String passportImg,String handImg,String positiveImg,String negativeImg){
        doRequest(buildRequest(mModel.uploadAuth(passportImg,handImg,positiveImg,negativeImg)),1);
    }

    public void getStoreMsg() {
        doRequest(buildRequest(mModel.getStoreMsg()),2);
    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what==1){
            mRootView.uploadSuccess(((AuthInfo) result.data));
        }else if (what==2){
            mRootView.getStoreErrorMsg(((ErrorBusiness) result.data));
        }
    }

}
