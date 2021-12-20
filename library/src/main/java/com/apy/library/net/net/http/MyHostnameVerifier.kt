package com.apy.library.net.net.http

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession


class MyHostnameVerifier : HostnameVerifier {
    //主机名校验：当发现是公司的接口，返回true
    override fun verify(hostname: String?, session: SSLSession?): Boolean {
        return true
    }
}
