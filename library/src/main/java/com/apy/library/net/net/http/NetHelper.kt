package com.apy.library.net.net.http

/**
 * 已废弃
 * 原因:
 * retrofit不能解析二次泛型, 无法直接生成实体类;
 * Gson无法直接解析实体类包含List
 *
 * 现改为用协程手动解析
 */

/*private fun http(disposables: CompositeDisposable, config: NetWrapper<String>.() -> Unit) {
    val wrapper = NetWrapper<String>()
    wrapper.config()
    val api = NetClient.getClient().create(API::class.java)
    with(wrapper) {
        when (method) {
            Method.GET -> api.getRawData(url, params)
            Method.POST -> api.postRawData(url, params)
        }
    }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { disposables.add(it) }
            .subscribe(
                    {
                        wrapper.onSuccess(it.string())
                    },
                    { wrapper.onError(it.message.toString()) }
            )
}*/

/*private inline fun <reified T> httpBase(disposables: CompositeDisposable, config: NetWrapper<T>.() -> Unit) {
    val wrapper = NetWrapper<T>()
    wrapper.config()
    val api = NetClient.getClient().create(API::class.java)
    with(wrapper) {
        when (method) {
            Method.GET -> api.getBaseData<T>(url, params)
            Method.POST -> api.postBaseData<T>(url, params)
        }
    }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { disposables.add(it) }
            .subscribe(
                    {
                        it.info?.let {info->wrapper.onSuccess(info)}

                    },
                    { wrapper.onError(it.message.toString()) }
            )
}*/
