package com.johnrambo.ktea.net.websocket

/**
 * Create by JohnRambo at 2018/8/18
 */

fun websocket(config: SocketWrapper.() -> Unit): WebSocketLite {
    val wrapper = SocketWrapper()
    wrapper.config()
    return WebSocketLite.instance(wrapper)
}

class SocketWrapper {
    lateinit var success: (message: String) -> Unit
    var open: ((isOpen: Boolean) -> Unit)? = null
    lateinit var close: (message: String) -> Unit
    lateinit var error: (message: String) -> Unit
}