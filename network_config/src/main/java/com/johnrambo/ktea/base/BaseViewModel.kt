package com.johnrambo.ktea.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

/**
 * Create by JohnRambo at 2018/8/17
 */
open class BaseViewModel : ViewModel() {
    protected val jobs by lazy { mutableListOf<Job>() }
//    protected val disposables by lazy { CompositeDisposable() }

    override fun onCleared() {
        close()
        super.onCleared()
    }

    private fun close() {
//        disposables.clear()
        jobs.forEach { it.cancel() }
    }
}