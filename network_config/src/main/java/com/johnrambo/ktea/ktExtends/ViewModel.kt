package com.johnrambo.ktea.ktExtends

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Create by JohnRambo at 2018/9/29
 */

fun <T : Any> LiveData<T>.observe(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, Observer {
        if (it != null) {
            observer(it)
        }
    })
}