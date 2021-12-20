

@file:Suppress("NOTHING_TO_INLINE")

package com.apy.library.net.ktExtends

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.PorterDuffXfermode


/**
 * Create by JohnRambo at 2018/7/13
 */

inline fun PorterDuff.Mode.toXfermode() = PorterDuffXfermode(this)

inline fun PorterDuff.Mode.toColorFilter(color: Int) = PorterDuffColorFilter(color, this)
