package com.wta.NewCloudApp.mvp.view;

import com.jess.arms.mvp.IView;

import java.util.List;

/**
 * Created by ASUS on 2018/6/6.
 */

public interface BaseDataView extends IView {
    void getData(int what,List data);
    void showListLoading();
    void hideListLoading();
}
