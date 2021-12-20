package com.apy.library.net

import android.app.Activity
import android.app.Application
import com.apy.library.net.net.http.JsonStyle
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import java.util.*

/**
 * Create by JohnRambo at 2018/9/7
 */


class Settings {

    companion object {
        private val settings by lazy { Settings() }
        private var isFirst = true

        fun init(config: Settings.() -> Unit) {
            if (isFirst) {
                settings.config()
                isFirst = false
            } else {
                throw RuntimeException("Settings不能多次初始化")
            }
        }

        val baseUrl by lazy { settings.baseUrl }
        fun activityStack() = settings.activityStack
        fun isDEBUG() = settings.isDEBUG
        fun websocketurl() = settings.websocketurl
        fun appCtx() = settings.appCtx
        fun objectStyle() = settings.objectStyle
        fun arrayStyle() = settings.arrayStyle
    }

    lateinit var activityStack: LinkedList<Activity> //保存所有activity的栈

    var isDEBUG = false //当前是否为debug模式

    lateinit var baseUrl: String //retrofit

    lateinit var websocketurl: String //websocket

    lateinit var appCtx: Application

    var objectStyle: JsonStyle? = null

    var arrayStyle: JsonStyle? = null

    init {
//        Logger.addLogAdapter(AndroidLogAdapter())
    }
}