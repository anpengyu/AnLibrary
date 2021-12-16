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

    /*
    判读时间差距，两个时间相差多少天，时，分，秒
     */
    fun getDay(date: String?): Long? {
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val now = df.parse(UIUtils.getDateTimeSecond()) //当前时间
        val date = df.parse(date) //过去
        val l = now.time - date.time
        val day = l / (24 * 60 * 60 * 1000)
        val hour = l / (60 * 60 * 1000) - day * 24
        val min = l / (60 * 1000) - day * 24 * 60 - hour * 60
        val s = l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60
//        println("" + day + "天" + hour + "小时" + min + "分" + s + "秒")
        return s
    }
}