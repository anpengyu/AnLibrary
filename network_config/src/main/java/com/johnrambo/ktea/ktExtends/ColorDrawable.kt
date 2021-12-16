
@file:Suppress("NOTHING_TO_INLINE", "WRONG_ANNOTATION_TARGET_WITH_USE_SITE_TARGET_ON_TYPE")

import android.graphics.drawable.ColorDrawable
import androidx.annotation.ColorInt

/**
 * Create by JohnRambo at 2018/7/13
 */

inline fun @receiver:ColorInt Int.toDrawable() = ColorDrawable(this)
