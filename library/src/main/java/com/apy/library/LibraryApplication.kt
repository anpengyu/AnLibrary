package com.apy.library

import android.app.Application

open class LibraryApplication : Application() {

    lateinit var pkgList: List<String>

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        var instance: LibraryApplication? = null
    }
}