package com.apy.library.net.net.websocket

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.apy.library.net.Settings
import com.apy.library.net.common.locked
import com.apy.library.net.common.logger
import com.apy.library.net.ktExtends.toURI
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.locks.ReentrantLock

/**
 * Create by JohnRambo at 2018/8/18
 *
 * webSocket请求类
 */

class WebSocketLite private constructor() {

    private lateinit var webSocketClient: WebSocketClient
    private val queue: Queue<String> = LinkedBlockingQueue()
    private val lock by lazy {
        ReentrantLock()
    }

    private val maxResetTime = 4
    private var currentResetTime = 0


    private lateinit var wrapper: SocketWrapper

    private val handler: Handler by lazy {
        object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    1 -> wrapper.error(msg.obj as String)
                    2 -> wrapper.close(msg.obj as String)
                    3 -> wrapper.open?.invoke(true)
                }
            }
        }
    }

    companion object {
        private val client by lazy {
            WebSocketLite()
        }

        fun instance(wrapper: SocketWrapper): WebSocketLite {
            client.wrapper = wrapper
            client.initWebSocket()
            return client
        }
    }

    fun send(message: String) {
        if (webSocketClient.isOpen) {
            webSocketClient.send(message)
        } else {
            locked(lock) {
                queue.offer(message)
            }
        }
    }

    fun close() {
        handler.removeCallbacksAndMessages(null)
        queue.clear()
        if (webSocketClient.isOpen)
            webSocketClient.close()
    }

    /**
     * 用于出现错误是重启socket
     */
    fun resetSocket() {
        if (currentResetTime > maxResetTime) {
            with(handler.obtainMessage()) {
                what = 1
                obj = "socket重连已达到最大次数"
                sendToTarget()
            }
        } else {
            locked(lock) {
                ++currentResetTime
            }
            close()
            initWebSocket()
        }
    }

    private fun initWebSocket() {
        webSocketClient = object : WebSocketClient(Settings.websocketurl().toURI()) {
            override fun onOpen(handshakedata: ServerHandshake?) {
                logger("websocket 连接成功")
                with(handler.obtainMessage()) {
                    what = 3
                    sendToTarget()
                }
                locked(lock) {
                    currentResetTime = 0
                    while (!queue.isEmpty()) {
                        send(queue.poll())
                    }
                }
            }

            override fun onClose(code: Int, reason: String, remote: Boolean) {
                val str = "WebSocket连接关闭, code:$code"
                logger(str)
                with(handler.obtainMessage()) {
                    what = 2
                    obj = str
                    sendToTarget()
                }

            }

            override fun onMessage(message: String) {
                logger("websocket 收到消息: $message")
                wrapper.success(message)
            }

            override fun onError(ex: Exception) {
                val str = "WebSocket发生错误: ${ex.message.toString()}"
                logger(str)
                with(handler.obtainMessage()) {
                    what = 1
                    obj = str
                    sendToTarget()
                }
            }
        }
        webSocketClient.connect()
    }
}