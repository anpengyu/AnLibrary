package com.apy.library.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

/**
 * 时间
 */
class DateUtils {

    // 判断两个日期大小  如，第一个日期大于第二个日期，返回true  反之false
    @SuppressLint("SimpleDateFormat")
    fun isDateOneBigger(str1: String, str2: String): Boolean {
        val isBigger: Boolean
        //输入的格式，选择性更改
        val sdf = SimpleDateFormat("hh:mm")
        isBigger = sdf.parse(str1).time >= sdf.parse(str2).time
        return isBigger
    }

    //日期比较，大于多少天
    @SuppressLint("SimpleDateFormat")
    fun dateCompare(str1: String, str2: String): Long {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val nativeTime = Calendar.getInstance()
        nativeTime.time = simpleDateFormat.parse(str1)

        val currentTime = Calendar.getInstance()
        currentTime.time = simpleDateFormat.parse(str2)
        return (currentTime.timeInMillis - nativeTime.timeInMillis) / (1000 * 3600 * 24)
    }

}