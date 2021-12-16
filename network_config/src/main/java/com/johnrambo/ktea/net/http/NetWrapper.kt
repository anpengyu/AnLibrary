package com.johnrambo.ktea.net.http

import com.apy.library.BaseActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File


/**
 * 请求头信息
 */
object RequestData {
    var token = ""
}

/**
 * 当时FILE的时候上传文件参数value必须为File类型或者string类型!!!
 */
enum class Method { GET, POST, FILE, POSTJSON, POSTHEADER }

class TokenLose

class NetWrapper<T> {
    lateinit var url: String
    var method = Method.GET
    var params: MutableMap<String, Any?>? = null
        get() = field ?: mutableMapOf()
    var appName = ""
    lateinit var activity: BaseActivity

    lateinit var onSuccess: (result: T) -> Unit
    lateinit var onError: (error: String) -> Unit
}

object HTTP {
    fun execute(
        method: Method,
        url: String,
        params: MutableMap<String, Any?>?,
        appName: String
    ): Response<ResponseBody> {
        if (params == null) throw java.lang.IllegalArgumentException("请求参数params不能为null!!!")
        val api = NetClient.getClient().create(API::class.java)
        return when (method) {
            Method.GET -> api.getCall(url, params)
            Method.POST -> api.postCall(url, params)
            Method.POSTHEADER -> api.postCall(url, params, appName)
            Method.POSTJSON -> api.postJsonCall(
                url,
                RequestBody.create(
                    MediaType.parse("application/json;charset=UTF-8"), (params["body"]
                        ?: "") as String
                )
            )
            //上传文件参数必须为File类型或者string类型
            Method.FILE -> {
                val builder = MultipartBody.Builder().setType(MultipartBody.FORM)

                val map = mutableMapOf<String, RequestBody>()
                for (param in params) {
                    val value = param.value
                    if (value != null) {
                        when (value) {
                            is File -> builder.addFormDataPart(
                                param.key,
                                value.name,
                                RequestBody.create(MediaType.parse("multipart/form-data"), value)
                            )
                            is String -> builder.addFormDataPart(param.key, value)
                            is Int -> builder.addFormDataPart(param.key, value.toString())
                            is Boolean -> builder.addFormDataPart(param.key, value.toString())
                            else -> throw IllegalArgumentException("上传文件请求的参数值必须为File/String/Int类型!!!")
                        }
                    }

                }
                api.postJsonCall(url, builder.build())
            }
        }.execute()
    }
}