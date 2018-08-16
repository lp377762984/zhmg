package com.wta.NewCloudApp.uitls;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.mvp.ui.listener.DialogCallback;

import java.util.Date;


public class DialogUtils {
    public static Dialog createWaitDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.wait_dialog);
        return dialog;
    }

    public static Dialog createAuthDialog(Context context) {
        Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.auth_layout);
        dialog.findViewById(R.id.im).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public static Dialog createUpdateDialog(Context context, View.OnClickListener listener) {
        Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.update_layout);
        dialog.findViewById(R.id.im_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_update).setOnClickListener(listener);
        return dialog;
    }

    public static Dialog showAlertDialog(Context context, String message, String leftBtn, String rightBtn, DialogCallback dialogClick) {
        final Dialog dialogAlterSure = new Dialog(context, R.style.dialog);
        View alertView = LayoutInflater.from(context).inflate(R.layout.alert_view_with_2_button, null);
        dialogAlterSure.setContentView(alertView);
        TextView tvMsg = (TextView) alertView.findViewById(R.id.tv_alert_msg);//提示信息
        TextView btSure = (TextView) alertView.findViewById(R.id.tv_alert_sure);//确定按钮
        TextView btCancel = (TextView) alertView.findViewById(R.id.tv_alert_cancel);//取消按钮
        btSure.setText(leftBtn == null ? "取消" : leftBtn);
        btCancel.setText(rightBtn == null ? "确定" : rightBtn);
        btSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogClick != null) {
                    dialogClick.handleLeft(dialogAlterSure);
                }
                dialogAlterSure.dismiss();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogClick != null) {
                    dialogClick.handleRight(dialogAlterSure);
                }
                dialogAlterSure.dismiss();
            }
        });
        tvMsg.setText(message);
        Window window = dialogAlterSure.getWindow();
        WindowManager.LayoutParams wlp;
        if (window != null) {
            wlp = window.getAttributes();
            wlp.width = (int) ScreenDpiUtils.dp2px(context, 275);
            window.setAttributes(wlp);
        }
        dialogAlterSure.show();
        return dialogAlterSure;
    }

    public static Dialog showAlertDialog(Context context, String message, DialogCallback dialogClick) {
        return showAlertDialog(context, message, null, null, dialogClick);
    }

    public static TimePickerView showTimePicker(Context cotext, OnTimeSelectListener listener) {
        return showTimePicker(cotext,listener,false, false, false, true, true, false);
    }

    public static TimePickerView showMonthTimePicker(Context cotext, OnTimeSelectListener listener) {
        return showTimePicker(cotext,listener,true, true, false, false, false, false);
    }

    public static TimePickerView showDayTimePicker(Context cotext, OnTimeSelectListener listener) {
        return showTimePicker(cotext,listener,true, true, true, false, false, false);
    }

    public static TimePickerView showTimePicker(Context cotext, OnTimeSelectListener listener,boolean year,boolean month,boolean day,boolean hour,boolean minute,boolean seconds) {
        TimePickerView pvTime = new TimePickerBuilder(cotext, listener)
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                    }
                })

                .setCancelColor(cotext.getResources().getColor(R.color.style_color))
                .setSubmitColor(cotext.getResources().getColor(R.color.style_color))
                .setContentTextSize(18)
                .setSubCalSize(16)
                .setLineSpacingMultiplier(1.7f)
                .setType(new boolean[]{year, month, day, hour, minute, seconds})
                .isDialog(true)
                .build();
        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(R.style.Animation_Bottom_Dialog);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
        return pvTime;
    }
}
