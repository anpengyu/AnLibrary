package com.apy.library.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.apy.library.R
import com.wang.avi.AVLoadingIndicatorView
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

object LoadingUtil {

    private var dialog: AlertDialog? = null

    @SuppressLint("CheckResult")
    fun showLoading(context: Context?) {
        val view =
            LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
        dialog = AlertDialog.Builder(context).setView(view).create()
        dialog?.window
            ?.setBackgroundDrawableResource(android.R.color.transparent)
        val avliv =
            view.findViewById<View>(R.id.avi) as AVLoadingIndicatorView
        avliv.show()
        dialog?.setCancelable(true)
        dialog?.show()


        //10秒之后关闭弹窗
        Observable.timer(10, TimeUnit.SECONDS).subscribe {
            if (dialog != null && dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        }
    }

    fun hideLoading() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }
}