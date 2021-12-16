package com.apy.library.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apy.library.R;
import com.wang.avi.AVLoadingIndicatorView;

public class ShowDialog {
    private static ShowDialog instance;
    private static AlertDialog dialog;
    private static TextView loadingTv;
    private final Activity activity;

    private ShowDialog(Activity activity) {
        this.activity = activity;
        createLoadingDialog(activity, false);
    }

    public static ShowDialog getInstance(Activity activity) {
        if (instance == null) {
            instance = new ShowDialog(activity);
        }
        return instance;
    }

    public void show(String msg) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadingTv != null) {
                    loadingTv.setText("");
                    if( msg != null){
                        loadingTv.setText(msg);
                    }
                }

                show();
            }
        });

    }

    public void show() {
        dialog.show();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(10000);
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static void createLoadingDialog(Context context, boolean cancelable) {
        View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog, null);
        dialog = new AlertDialog.Builder(context).setView(view).create();
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        AVLoadingIndicatorView avliv = view.findViewById(R.id.dialog_view);
        loadingTv = view.findViewById(R.id.loading_tv);
        avliv.show();
        dialog.setCancelable(false);
    }
}
