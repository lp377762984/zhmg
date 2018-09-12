package com.wta.NewCloudApp.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.wta.NewCloudApp.mvp.model.entity.Bill;
import com.wta.NewCloudApp.mvp.model.entity.Business;
import com.wta.NewCloudApp.mvp.model.entity.HomeBanner;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.mvp.model.entity.Update;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;


public interface HomeContract {
    interface View extends IView {
        void showList(Result<List<Bill>> bills);
        void showBState(Result<Business> businessResult);

        void showHomeBanner(List<HomeBanner> homeBanners);

        void stopRefresh();

        void showListFailed();

        void showUpdate(Update update);

        void showProgress();

        void updateProgress(int progress);
    }

    interface Model extends IModel {
        Observable<Result<List<Bill>>> getBillList();
        Observable<Result<Business>> getStoreState();
        Observable<Result<List<HomeBanner>>> getHomeBanner();

        Observable<Result<Update>> checkUpdate(String packageVersion);

        Observable<ResponseBody> downloadApp(String url);
    }
}
