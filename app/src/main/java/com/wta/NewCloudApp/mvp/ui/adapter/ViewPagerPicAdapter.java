package com.wta.NewCloudApp.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.model.entity.PictureC;
import com.wta.NewCloudApp.mvp.ui.widget.EditTextHint;

import java.util.ArrayList;

public class ViewPagerPicAdapter extends PagerAdapter {
    private ArrayList<PictureC> pictures;

    public ArrayList<PictureC> getData() {
        return pictures;
    }

    public ViewPagerPicAdapter(ArrayList<PictureC> pictures) {
        this.pictures = pictures;
    }

    @Override
    public int getCount() {
        return pictures.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Context mContext = container.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.pic_det_item, null);
        EditTextHint etDesc = (EditTextHint) view.findViewById(R.id.et_desc);
        etDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                pictures.get(position).desc = s.toString();
            }
        });
        ImageView imageView = (ImageView) view.findViewById(R.id.im_pic);
        PictureC item = pictures.get(position);
        if (!TextUtils.isEmpty(item.url))
            GlideArms.with(mContext).load(item.url).placeholder(R.mipmap.side_b_placeholder).into(imageView);
        else
            GlideArms.with(mContext).load(item.file).placeholder(R.mipmap.side_b_placeholder).into(imageView);
        etDesc.setText(item.desc);
        etDesc.setSelection(etDesc.length());
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
