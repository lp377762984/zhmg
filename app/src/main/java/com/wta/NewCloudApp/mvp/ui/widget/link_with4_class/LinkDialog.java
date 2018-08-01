package com.wta.NewCloudApp.mvp.ui.widget.link_with4_class;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wta.NewCloudApp.R;
import com.wta.NewCloudApp.uitls.ScreenDpiUtils;


public class LinkDialog extends Dialog {

    private AddressSelector selector2;
    public LinkDialog(Context context) {
        super(context, R.style.bottom_dialog);
        init(context);
    }

    private void init(Context context) {
        selector2 = new AddressSelector(context);
        View view = selector2.getView();
        view.findViewById(R.id.im_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setContentView(view);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = (int) ScreenDpiUtils.dp2px(context, 310);
        window.setAttributes(params);

        window.setGravity(Gravity.BOTTOM);
    }

    public void setOnAddressSelectedListener(OnAddressSelectedListener listener) {
        dismiss();
        this.selector2.setOnAddressSelectedListener(listener);
    }
}
