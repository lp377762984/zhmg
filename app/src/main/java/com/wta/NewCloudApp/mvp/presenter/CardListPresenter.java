package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.mvp.contract.CardListContract;
import com.wta.NewCloudApp.mvp.model.UserModel;
import com.wta.NewCloudApp.mvp.model.entity.BankCard;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.view.BaseDataView;

import java.util.List;

import javax.inject.Inject;


@ActivityScope
public class CardListPresenter extends BBasePresenter<UserModel, CardListContract.View> {


    @Inject
    public CardListPresenter(UserModel model, CardListContract.View rootView) {
        super(model, rootView);
    }

    public void getCardList(boolean isRefresh) {
        doRequest(buildListRequest(mModel.getCardList()), 1);
    }

    public void deleteCard(int id) {
        doRequest(buildRequest(mModel.delBankCard(id)), 2);
    }


    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        if (what == 1) {
            ((BaseDataView) mRootView).getData(what, (Result<List>) result);
        } else if (what == 2) {
            mRootView.deleteSuccess();
        }
    }
}
