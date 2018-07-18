package com.wta.NewCloudApp.uitls;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.wta.NewCloudApp.jiuwei210278.R;


public class DialogUtils {
    public static Dialog createWaitDialog(Context context){
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.wait_dialog);
        return dialog;
    }

    public static Dialog createAuthDialog(Context context){
        Dialog dialog=new Dialog(context,R.style.dialog);
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

}
