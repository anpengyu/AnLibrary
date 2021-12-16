package com.apy.library

import android.app.Application
import com.apy.library.utils.UIUtils

open class LibraryApplication : Application() {

    lateinit var pkgList: List<String>

    override fun onCreate() {
        super.onCreate()
        instance = this
        UIUtils.init(this)
    }

    companion object {
        var instance: LibraryApplication? = null
    }
}