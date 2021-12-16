package com.johnrambo.ktea.net.http

import androidx.collection.ArrayMap
import com.johnrambo.ktea.Settings
import com.johnrambo.ktea.net.http.interceptor.HttpHeadInterceptor
import com.johnrambo.ktea.net.http.interceptor.HttpLoggingInterceptor
import com.johnrambo.ktea.net.http.interceptor.NoneInterceptor
import okhttp3.*
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit

object NetClient {

    fun getClient() = retrofitClient


    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            //HTTPS 验证自己的证书
//            .sslSocketFactory(MySSLSocketFactory.createSSLSocketFactory(Settings.appCtx()))
//            //主机名校验
//            .hostnameVerifier(MyHostnameVerifier())
            .cache(Cache(File(Settings.appCtx().cacheDir, "HttpResponseCache"), 50 * 1024 * 1024))
            .cookieJar(object : CookieJar {
                var cookieStore = ArrayMap<HttpUrl, List<Cookie>?>()
                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>?) {
                    cookieStore[url] = cookies
                }

                override fun loadForRequest(url: HttpUrl?): List<Cookie> {
                    return cookieStore[url] ?: mutableListOf()
                }

            })
            .addInterceptor(HttpHeadInterceptor())
            .addInterceptor(if (Settings.isDEBUG()) HttpLoggingInterceptor() else NoneInterceptor())
            .build()
    }

    /**
     * 最新使用的是手动fastjson解析和协程网络请求, 所以不需要这两种adapter了
     */
    private val retrofitClient: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Settings.baseUrl)
            .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}