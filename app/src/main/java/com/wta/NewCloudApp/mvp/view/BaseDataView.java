package com.wta.NewCloudApp.mvp.view;

import com.jess.arms.mvp.IView;
import com.wta.NewCloudApp.mvp.model.entity.Result;

import java.util.List;

/**
 * Created by ASUS on 2018/6/6.
 */

public interface BaseDataView extends IView {
    void getData(int what, Result<List> result);
    void showListLoading();
    void hideListLoading();
    void loadFailed();
}
