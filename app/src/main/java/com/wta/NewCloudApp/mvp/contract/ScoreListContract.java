package com.wta.NewCloudApp.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.wta.NewCloudApp.mvp.model.entity.BillType;
import com.wta.NewCloudApp.mvp.view.BaseDataView;

import java.util.List;


public interface ScoreListContract {

    interface View extends IView {

        void showBillsType(List<BillType> billType);

        Activity getMContext();
    }

}
