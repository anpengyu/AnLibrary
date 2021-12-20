package com.apy.library.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.apy.library.R;

public class AToast {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public static void showTextToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    public static void showTextLongToast(String msg) {
        showToast(msg, Toast.LENGTH_LONG);
    }

    @SuppressLint("ShowToast")
    private static void showToast(String msg, int duration) {

    }

    public static void ToastMessage(Activity activity, String messages) {
        ToastMessage(activity,messages,Toast.LENGTH_SHORT);
    }

    /**
     * 将Toast封装在一个方法中，在其它地方使用时直接输入要弹出的内容即可
     */
    public static void ToastMessage(Activity activity, String messages,int time) {
        HANDLER.post(() -> {
            LayoutInflater inflater = activity.getLayoutInflater();
            @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_commom, null); //加載layout下的布局
            TextView text = view.findViewById(R.id.content);
            text.setText(messages); //toast内容
            Toast toast = new Toast(activity.getApplicationContext());
            toast.setGravity(Gravity.CENTER, 0, 0);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
            toast.setDuration(time);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
            toast.setView(view); //添加视图文件
            toast.show();
        });

    }
}
