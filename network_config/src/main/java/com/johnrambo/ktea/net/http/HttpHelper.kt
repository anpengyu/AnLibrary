package com.johnrambo.ktea.net.http

import com.apy.library.BaseActivity
import com.apy.library.utils.AToast
import com.apy.library.utils.LoadingUtil
import com.johnrambo.ktea.Settings
import com.johnrambo.ktea.common.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject

/**
 * Create by JohnRambo at 2018/8/30
 * 踩坑后重构的网络连接库:
 * 1. 使用协程实现
 * 2. 使用fastjson手动解析, 可以直接使用BaseInfo类二次泛型得到实体类
 * 3. entity只能使用java类, 因为自动生成的kotlin没有空构造方法, fastjson无法解析
 * 4. 上传文件参数必须为File类型或者string类型
 */

fun http(config: NetWrapper<String>.() -> Unit): Job {
    val wrapper = NetWrapper<String>()
    wrapper.config()
    with(wrapper) {
        return launchIO {
            var result: String? = null
            var error: String? = null
            try {
                val response = HTTP.execute(method, url, params, appName)
                if (response.isSuccessful) {
                    result = response.body()!!.string().trim()
                } else {
                    error = "code:${response.code()} ; 连接错误:${response.errorBody()!!.string()}"
                }
            } catch (e: Exception) {
                error = "错误:${e.message}"
            }

            launchUI {
                result?.let { onSuccess(it) }
                error?.let { onError(it) }
            }.join()
        }
    }
}

//这里的泛型是返回数据解析后的object
inline fun <reified T> httpObject(config: NetWrapper<T>.() -> Unit): Job {
    val wrapper = NetWrapper<T>()
    wrapper.config()
    with(wrapper) {
        return launchIO {
            var result: T? = null
            var error: String? = null
            try {
                val response = HTTP.execute(method, url, params, appName)
                if (response.isSuccessful) {
                    val bodyString = response.body()!!.string().trim()
                    result = bodyString.parseObject<T>()
                } else {
                    error = "code:${response.code()} ; 连接错误:${response.errorBody()!!.string()}"
                }
            } catch (e: Exception) {
                error = "错误:${e.message}"
            }
            launchUI {
                result?.let { onSuccess(it) }
                error?.let { onError(it) }
            }.join()
        }
    }
}

/**
 * 这里的泛型是BaseInfo中的info解析后的object
 */
inline fun <reified T> httpBase(config: NetWrapper<T>.() -> Unit) =
    httpBase(Settings.objectStyle(), config)

inline fun <reified T> httpBase(style: JsonStyle?, config: NetWrapper<T>.() -> Unit): Job {
    if (style == null) throw IllegalArgumentException("http请求解析成指定的实体类必须指定JsonStyle!!!")
    val wrapper = NetWrapper<T>()
    wrapper.config()
    with(wrapper) {
        return launchIO {
            var result: T? = null
            var error: String? = null
            try {
                LoadingUtil.hideLoading()
                val response = HTTP.execute(method, url, params, appName)
                if (response.isSuccessful) {
                    val json = response.body()!!.string().trim().toJsonObject()
                    result = json.toString()
                        .parseObject<T>()


                } else {
                    error = "code:${response.code()} ; 连接错误:${response.errorBody()!!.string()}"
                    LoadingUtil.hideLoading()
                }
            } catch (e: Exception) {
                error = "错误:${e.message}"
                LoadingUtil.hideLoading()
            }
            launchUI {
                result?.let { it -> onSuccess(it) }
                error?.let { onError(it) }
            }.join()
        }
    }
}

/**
 * 这里的泛型是BaseInfo中的info解析后生成的List<T>
 */
inline fun <reified T> httpBaseList(config: NetWrapper<List<T>>.() -> Unit): Job {
    return if (Settings.arrayStyle() == null) {
        if (Settings.objectStyle() == null) {
            throw IllegalArgumentException("http请求解析成指定的实体类必须指定JsonStyle!!!")
        } else {
            httpBaseList(Settings.objectStyle(), config)
        }
    } else {
        httpBaseList(Settings.arrayStyle(), config)
    }
}

inline fun <reified T> httpBaseList(
    style: JsonStyle?,
    config: NetWrapper<List<T>>.() -> Unit
): Job {
    if (style == null) throw IllegalArgumentException("http请求解析成指定的实体类必须指定JsonStyle!!!")
    val wrapper = NetWrapper<List<T>>()
    wrapper.config()
    with(wrapper) {
        return launchIO {
            var result: List<T>? = null
            var error: String? = null
            try {
                val response = HTTP.execute(method, url, params, appName)
                if (response.isSuccessful) {
                    val json = response.body()!!.string().trim().toJsonObject()
                    /*val status = json.getString(style.statusName)
                    if (style.tokenLoseStatusValue != null) {
                        if (status == style.tokenLoseStatusValue) RxBus.BUS.post(TokenLose())
                    }
                    if (status == style.successStatusValue) {
                        val dataString = json.getString(style.dataName).trim()
                        result = if (dataString.isNotEmpty()) dataString.parseArray<T>() else null
                    } else {
                        error = json.getString(style.messageName)
                    }*/
                    val dataString = json.getString(style.dataName).trim()
                    result = if (dataString.isNotEmpty()) dataString.parseArray<T>() else null
                } else {
                    error = "code:${response.code()} ; 连接错误:${response.errorBody()!!.string()}"
                }
            } catch (e: Exception) {
                error = "错误:${e.message}"
            }
            launchUI {
                result?.let { onSuccess(it) }
                error?.let { onError(it) }
            }.join()
        }
    }
}