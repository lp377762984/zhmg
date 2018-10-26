package com.wta.NewCloudApp.mvp.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wta.NewCloudApp.R;

public class NumberBtn extends LinearLayout implements View.OnClickListener {

    private TextView etNumber;
    private int maxNumber = Integer.MAX_VALUE;
    private BtnListener listener;

    public interface BtnListener {
        void clickAdd(int num);
        void clickMinus(int num);
    }

    public BtnListener getListener() {
        return listener;
    }

    public void setListener(BtnListener listener) {
        this.listener = listener;
    }

    public NumberBtn(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NumberBtn(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.number_btn, this);
        findViewById(R.id.btn_minus).setOnClickListener(this);
        findViewById(R.id.btn_add).setOnClickListener(this);
        etNumber = findViewById(R.id.et_number);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_minus:
                int num = Integer.parseInt(etNumber.getText().toString());
                if (num == 1) return;
                etNumber.setText((num - 1) + "");
                if (listener!=null) listener.clickMinus(num-1);
                break;
            case R.id.btn_add:
                int afterNum = Integer.parseInt(etNumber.getText().toString()) + 1;
                if (afterNum > maxNumber) {
                    Toast.makeText(getContext(), "库存不足！", Toast.LENGTH_SHORT).show();
                    return;
                }
                etNumber.setText(afterNum + "");
                if (listener!=null) listener.clickAdd(afterNum);
                break;
        }
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }
}
