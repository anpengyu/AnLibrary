package com.johnrambo.ktea.net.http

import android.content.Context
import java.io.ByteArrayOutputStream
import java.net.URL
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.*

object MySSLSocketFactory {

    fun createSSLSocketFactory(context: Context): SSLSocketFactory {
        //val url = URL("http://192.168.3.24:8080/test_json.json")
        //  var connection: HttpURLConnection = url.openConnection() as HttpURLConnection

        //1、创建SSL上下文对象设置任何管理器（相当于保安）
        val sslContext = SSLContext.getInstance("TLS")

        //获取信任管理器工厂
        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        //初始化工厂对象
        val ks = KeyStore.getInstance(KeyStore.getDefaultType())
        //清空默认证书信息，设置自己的证书
        ks.load(null)
        val cf = CertificateFactory.getInstance("X.509")
        val open = context.assets.open("MyTest.cer")
        val cert = cf.generateCertificate(open)
        ks.setCertificateEntry("MyTest", cert)

        tmf.init(ks)
        val tm = tmf.trustManagers
        sslContext.init(null, tm, null)


        /* val url = URL("https://192.168.3.24:8443/test_json.json")
         var connection: HttpsURLConnection = url.openConnection() as HttpsURLConnection
         connection.requestMethod = "GET"
         connection.connectTimeout = 10 * 1000

         //使用信任管理器
         connection.sslSocketFactory = sslContext.socketFactory
         //校验主机名
         connection.hostnameVerifier = MyHostnameVerifier()

         //获取服务器返回流
         val ins = connection.inputStream
         //转成字符串
         val bos = ByteArrayOutputStream()
         val buffer = ByteArray(1024)
         var len = 0
         len = ins.read(buffer)
         while (len != -1) {
             bos.write(buffer, 0, len)
             len = ins.read(buffer)
         }

         val result = bos.toString()*/
        return sslContext.socketFactory
    }
}