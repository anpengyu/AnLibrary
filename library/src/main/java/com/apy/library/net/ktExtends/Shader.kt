package com.apy.library.net.ktExtends

import android.graphics.Matrix
import android.graphics.Shader

/**
 * Create by JohnRambo at 2020/1/17
 */

inline fun Shader.transform(block: Matrix.() -> Unit) {
    val matrix = Matrix()
    getLocalMatrix(matrix)
    block(matrix)
    setLocalMatrix(matrix)
}
