

@file:Suppress("NOTHING_TO_INLINE")

package com.apy.library.net.ktExtends


import android.net.Uri
import java.io.File
import java.net.URI

/**
 * Create by JohnRambo at 2018/7/13
 */

inline fun String.toUri(): Uri = Uri.parse(this)

inline fun String.toURI():URI = URI(this)

inline fun File.toUri(): Uri = Uri.fromFile(this)

inline fun Uri.toFile(): File = File(path)
