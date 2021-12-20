package com.apy.library.net.common

/**
 * Create by JohnRambo at 2018/8/18
 *
 * Gson太难用, 已废弃.......... 改用fastjson
 */
/*
object GSON {
    val gson by lazy { Gson() }
    val parser by lazy { JsonParser() }

    */
/**
     * 把string生成实体类对象
     *//*

    inline fun <reified T> toObject(json: String?) = gson.fromJson(json, T::class.java)

    */
/**
     * 把string生成实体类集合
     *//*

    inline fun <reified T> toList(json: String?) :List<T> {
        val type = object : ParameterizedType {
            override fun getOwnerType()= null
            override fun getActualTypeArguments()= arrayOf(T::class.java)
            override fun getRawType() = ArrayList::class.java
        }
        return gson.fromJson(json,type)
    }

    */
/**
     * 把string生成json对象
     *//*

    fun toJsonObject(json: String?) = parser.parse(json).asJsonObject

    */
/**
     * 把string生成json数组
     *//*

    fun toJsonArray(json: String?) = parser.parse(json).asJsonArray

}*/
