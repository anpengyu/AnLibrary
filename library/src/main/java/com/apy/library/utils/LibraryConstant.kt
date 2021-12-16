package com.apy.library.utils

interface LibraryConstant {
    companion object {

        const val GUARD_REVICE_STATE = "GUARD_REVICE_STATE"
        const val APP_VERSION_NAME = "APP_VERSION_NAME"

        //连续崩溃多次之后主进程给守护进程发送广播
        const val ERR_MAIN_SEND_BROADCAST = "MAIN_SEND_BROADCAST"

        //守护进程向主进程发送
        const val SEND_GUARD_REVICE_MSG = "com.ijx.guardcourse.broadcastreceiver"

        //主进程向守护进程发送
        const val SEND_MAIN_REVICE_MSG = "com.ijx.videophone.broadcastreceiver"

        const val SETTING_PSW = "20210518"//设置界面密码
        const val TEST_PSW = "20210225";
        const val SETTING_SAFE_PSW = "20210518165"//设置界面安全密码
        const val DEFAULT_CALL_TIME = "180"//默认最大通话时长
    }
}