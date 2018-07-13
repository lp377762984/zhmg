package com.wta.NewCloudApp.mvp.view;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.mvp.IView;

import java.util.List;

/**
 * Created by ASUS on 2018/6/5.
 */

public interface ILoadRefreshView<T> extends IView {
    Context getContext();
    void setData(List<T> data);
    BaseQuickAdapter getAdapter();
    boolean isRefresh();
    boolean isComplete();
}
