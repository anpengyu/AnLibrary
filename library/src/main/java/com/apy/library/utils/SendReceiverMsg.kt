package com.apy.library.utils

import android.content.Context
import android.content.Intent

//发送广播
object SendReceiverMsg {

    //守护进程向主进程发送
    fun sendGuardMsg(context: Context, state: String) {
        val intent = Intent(LibraryConstant.SEND_GUARD_REVICE_MSG)
        intent.putExtra(LibraryConstant.GUARD_REVICE_STATE, state)
        context.sendBroadcast(intent)
    }

    fun sendVideoMsg(context: Context, state: String) {
        sendVideoMsg(context, state, null)
    }

    //主进程向守护进程发送
    fun sendVideoMsg(context: Context, state: String, versionName: String?) {
        val intent = Intent(LibraryConstant.SEND_MAIN_REVICE_MSG)
        intent.putExtra(LibraryConstant.GUARD_REVICE_STATE, state)
        intent.putExtra(LibraryConstant.APP_VERSION_NAME, versionName)
        context.sendBroadcast(intent)
    }
}