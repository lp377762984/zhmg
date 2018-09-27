package com.wta.NewCloudApp.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.PictureC;
import com.wta.NewCloudApp.mvp.ui.adapter.ViewPagerPicAdapter;
import com.wta.NewCloudApp.mvp.ui.widget.MoneyBar;
import com.wta.NewCloudApp.uitls.FinalUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import timber.log.Timber;

/**
 * 图片详情
 */
public class PicDetActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ArrayList<PictureC> pictures;
    private ArrayList<PictureC> picturesCopy=new ArrayList<>();
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.moneyBar)
    MoneyBar moneyBar;
    private int position;
    ViewPagerPicAdapter adapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_pic_det;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        pictures = (ArrayList<PictureC>) getIntent().getSerializableExtra("pictures");
        createCopy();
        position = getIntent().getIntExtra("position", 0);
        adapter = new ViewPagerPicAdapter(pictures);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(this);
        setMoneyBarTitle();
        moneyBar.setCallBack(moneyBar.new CallbackImp() {
            @Override
            public void clickTail() {
                adapter.getData().remove(position);
                adapter.notifyDataSetChanged();
                if (pictures.size() == 0) backResults();
            }

            @Override
            public void clickBack(View back) {
                backResults();
            }
        });

    }

    private void createCopy() {
        for (int i = 0; i < pictures.size(); i++) {
            PictureC clone = pictures.get(i).clone();
            picturesCopy.add(clone);
        }
    }

    public static void startPicDet(Activity activity, ArrayList<PictureC> pictures, int position) {
        Intent intent = new Intent(activity, PicDetActivity.class);
        intent.putExtra("pictures", pictures);
        intent.putExtra("position", position);
        activity.startActivityForResult(intent, FinalUtils.REQUEST_PIC_DET);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.position = position;
        setMoneyBarTitle();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setMoneyBarTitle() {
        moneyBar.setTextTitle((position + 1) + "/" + pictures.size());
    }

    @Override
    public void onBackPressed() {
        backResults();
    }

    private void backResults() {
        Intent intent = getIntent();
        intent.putExtra("pictures", pictures);
        intent.putExtra("isNeedChange", isNeedChange());
        setResult(RESULT_OK, intent);
        finish();
    }

    private boolean isNeedChange() {
        if (pictures.size() != picturesCopy.size())
            return true;
        for (int i = 0; i < pictures.size(); i++) {
            PictureC pictureC = pictures.get(i);
            PictureC pictureC1 = picturesCopy.get(i);
            String desc = pictureC.desc;
            String desc1 = pictureC1.desc;
            if (desc!=null){
                if (!desc.equals(desc1)){
                    return true;
                }
            }
        }
        return false;
    }
}

