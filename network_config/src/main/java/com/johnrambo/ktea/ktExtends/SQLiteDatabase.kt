package com.lefen.market.common.ktExtends

import android.database.sqlite.SQLiteDatabase

/**
 * Create by JohnRambo at 2018/7/13
 */

inline fun <T> SQLiteDatabase.transaction(
        exclusive: Boolean = true,
        body: SQLiteDatabase.() -> T
): T {
    if (exclusive) {
        beginTransaction()
    } else {
        beginTransactionNonExclusive()
    }
    try {
        val result = body()
        setTransactionSuccessful()
        return result
    } finally {
        endTransaction()
    }
}