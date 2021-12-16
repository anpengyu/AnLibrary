package com.apy.library.utils

object GuardConstants {
    //守护进程

    //还活着，但是没有守护
    const val SELF_STATE_LIVE = "SELF_STATE_LIVE"

    //还活着，正在守护
    const val SELF_STATE_LIVE_GUARD = "SELF_STATE_LIVE_GUARD"

    //将主进程置为前台
    const val SET_TOP_MAIN_APP = "SET_TOP_MAIN_APP"

    //守护进程停止运行
    const val SELF_STATE_LIVE_STOP = "SELF_STATE_LIVE_STOP"

    //开启守护进程
    const val START_GUARD_SERVICE: String = "START_GUARD_SERVICE"

    //暂停
    const val PAUSE_GUARD_SERVICE = "STOP_GUARD_SERVICE"
    //继续
    const val RESUME_GUARD_SERVICE = "RESUME_GUARD_SERVICE"
}