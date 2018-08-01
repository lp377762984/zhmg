package com.wta.NewCloudApp.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bigkoo.pickerview.view.four.OnOptionsSelectListener4;
import com.bigkoo.pickerview.view.four.OptionsPickerBuilder4;
import com.bigkoo.pickerview.view.four.OptionsPickerView4;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.config.App;
import com.wta.NewCloudApp.mvp.model.entity.Province;
import com.wta.NewCloudApp.uitls.DataUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
    private ArrayList<Province> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<Province.City>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<Province.City.District>>> options3Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<ArrayList<Province.City.District.Street>>>> options4Items=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        task.execute();
    }

    public void showDiloge(View view) {
        if (options1Items.size() == 0) {
            Toast.makeText(this, "数据正在初始化", Toast.LENGTH_SHORT).show();
            return;
        }
        OptionsPickerView4 pvOptions = new OptionsPickerBuilder4(this, new OnOptionsSelectListener4() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, int options4, View v) {

            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items, options4Items);
        pvOptions.show();
    }

    @SuppressLint("StaticFieldLeak")
    private AsyncTask<Void, Void, ArrayList<Province>> task = new AsyncTask<Void, Void, ArrayList<Province>>() {
        @Override
        protected ArrayList<Province> doInBackground(Void... voids) {
            String json = DataUtils.getJson(App.getInstance(), "region.json");
            try {
                return parseJson(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Province> provinces) {
            TestActivity.this.options1Items.addAll(provinces);
            for (int i = 0; i < provinces.size(); i++) {//所有省
                Province province = provinces.get(i);
                ArrayList<Province.City> citys = province.getCitys();
                options2Items.add(citys);
                ArrayList<ArrayList<Province.City.District>> diss = new ArrayList<>();
                for (int j = 0; j < citys.size(); j++) {//所有市
                    Province.City city = citys.get(j);
                    ArrayList<Province.City.District> districts = city.getDistricts();
                    diss.add(districts);
                }
                options3Items.add(diss);
            }
        }

        private ArrayList<Province> parseJson(String json) throws JSONException {
            JSONObject jsonObject = new JSONObject(json);
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
    };


}
