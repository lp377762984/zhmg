package com.wta.NewCloudApp.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.mvp.contract.AddAddressContract;
import com.wta.NewCloudApp.mvp.model.UserModel;
import com.wta.NewCloudApp.mvp.model.entity.Address;
import com.wta.NewCloudApp.mvp.model.entity.Province;
import com.wta.NewCloudApp.mvp.model.entity.Result;
import com.wta.NewCloudApp.uitls.DataUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class AddAddressPresenter extends BBasePresenter<UserModel, AddAddressContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;

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

    public void parseLocalAddressData(){
        buildRequest(Observable.create(new ObservableOnSubscribe<ArrayList<Province>>() {
            @Override
            public void subscribe(ObservableEmitter<ArrayList<Province>> emitter) throws Exception {
                String json = DataUtils.getJson(App.getInstance(), "region.json");
                emitter.onNext(parseJson(json));
                emitter.onComplete();
            }
        })).subscribe(new ErrorHandleSubscriber<ArrayList<Province>>(mErrorHandler) {
            @Override
            public void onNext(ArrayList<Province> provinces) {
                mRootView.initAddressSuccess(provinces);
            }
        });

    }

    @Override
    public <T> void handle200(int what, Result<T> result) {
        super.handle200(what, result);
        mRootView.showAddress((Address) result.data);
    }
    private ArrayList<Province> parseJson(String json) throws JSONException {
        JSONObject jsonObject=new JSONObject(json);
        JSONArray p = jsonObject.getJSONArray("province");
        ArrayList<Province> data = new ArrayList<>();
        for (int i = 0; i < p.length(); i++) {
            JSONObject pObj = p.optJSONObject(i);
            int id = pObj.optInt("id");
            String name = pObj.optString("name");
            JSONArray c = pObj.optJSONArray("city");
            ArrayList<Province.City> citys = new ArrayList<>();
            for (int j = 0; j < c.length(); j++) {
                JSONObject dObj = c.optJSONObject(j);
                int cid = dObj.optInt("id");
                String cname = dObj.optString("name");
                JSONArray d = dObj.optJSONArray("district");
                ArrayList<Province.City.District> districts = new ArrayList<>();
                for (int k = 0; k < d.length(); k++) {
                    JSONObject kObj = d.optJSONObject(k);
                    int did = kObj.optInt("id");
                    String dname = kObj.optString("name");
                    Province.City.District district = new Province.City.District(did, dname);
                    districts.add(district);
                }
                Province.City city = new Province.City(cid, cname, districts);
                citys.add(city);
            }
            Province province = new Province(id, name, citys);
            data.add(province);
        }
        return data;
    }
}
