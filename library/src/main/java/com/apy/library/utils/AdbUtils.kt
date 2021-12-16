package com.apy.library.utils

import android.util.Log
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

object AdbUtils {

    fun adbOrder(command: String): Boolean {
        var result = false
        var dataOutputStream: DataOutputStream? = null
        var errorStream: BufferedReader? = null
        try {
            // 申请su权限
            val process = Runtime.getRuntime().exec("su")
            dataOutputStream = DataOutputStream(process.outputStream)
            // 执行pm install命令
            dataOutputStream.write(command.toByteArray(StandardCharsets.UTF_8))
            dataOutputStream.flush()
            dataOutputStream.writeBytes("exit\n")
            dataOutputStream.flush()
            process.waitFor()
            errorStream = BufferedReader(InputStreamReader(process.errorStream))
            var msg = ""
            var line: String
            // 读取命令的执行结果
            while (errorStream.readLine().also { line = it } != null) {
                msg += line
            }
            Log.d("AdbUtils", msg)
            // 如果执行结果中包含Failure字样就认为是安装失败，否则就认为安装成功
            if (!msg.contains("Failure")) {
                result = true
            }
        } catch (e: Exception) {
            Log.e("AdbUtils", e.message, e)
        } finally {
            try {
                dataOutputStream?.close()
                errorStream?.close()
            } catch (e: IOException) {
                Log.e("AdbUtils", e.message, e)
            }
        }
        return result
    }

}