package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.BankCard;
import com.wta.NewCloudApp.mvp.model.entity.Result;


public interface AddCardContract {

    interface View extends IView {
        void getBankCard(Result<BankCard> cardResult);
    }

}
