package com.wta.NewCloudApp.uitls;

import android.app.Dialog;
import android.content.Context;

import com.wta.NewCloudApp.jiuwei210278.R;


public class DialogUtils {
    public static Dialog createWaitDialog(Context context){
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.wait_dialog);
        return dialog;
    }
}
