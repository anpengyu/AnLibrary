package com.johnrambo.ktea.common

import com.alibaba.fastjson.JSON
import org.json.JSONArray
import org.json.JSONObject

/**
 * Create by JohnRambo at 2018/8/30
 */

inline fun <reified T> String.parseObject() = JSON.parseObject(this, T::class.java)

inline fun <reified T> String.parseArray() = JSON.parseArray(this, T::class.java)

fun String.toJsonObject() = JSONObject(this)

fun String.toJsonArray() = JSONArray(this)