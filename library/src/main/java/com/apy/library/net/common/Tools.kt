package com.apy.library.net.common

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.apy.library.net.Settings
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.style.Circle
import com.orhanobut.logger.Logger
import java.util.concurrent.locks.Lock

/**
 * 打印log
 */
fun logger(string: String) {
    if (Settings.isDEBUG()) {
        Logger.d(string)
    }
}

/**
 * 在一个范围映射一个值到另一个范围
 */
fun mapValueFromRangeToRange(
        value: Double,
        fromLow: Double,
        fromHigh: Double,
        toLow: Double,
        toHigh: Double): Double {
    val fromRangeSize = fromHigh - fromLow
    val toRangeSize = toHigh - toLow
    val valueScale = (value - fromLow) / fromRangeSize
    return toLow + valueScale * toRangeSize
}

fun mapValueFromRangeToRange(
        value: Int,
        fromLow: Int,
        fromHigh: Int,
        toLow: Int,
        toHigh: Int): Double {
    val fromRangeSize = fromHigh - fromLow
    val toRangeSize = toHigh - toLow
    val valueScale = (value - fromLow).toDouble() / fromRangeSize
    return toLow + valueScale * toRangeSize
}

fun mapValueFromRangeToRange(
        value: Float,
        fromLow: Float,
        fromHigh: Float,
        toLow: Float,
        toHigh: Float): Double {
    val fromRangeSize = fromHigh - fromLow
    val toRangeSize = toHigh - toLow
    val valueScale = (value - fromLow).toDouble() / fromRangeSize
    return toLow + valueScale * toRangeSize
}

/**
 *  多线程中通过锁同步
 */
fun <T> locked(lock: Lock, body: () -> T): T {
    lock.lock()
    try {
        return body()
    } finally {
        lock.unlock()
    }
}
