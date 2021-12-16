package com.johnrambo.ktea.base

import android.location.LocationListener
import android.os.Bundle

/**
 * Create by JohnRambo at 2018/12/19
 */
abstract class LiteLocationListener : LocationListener {

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) = Unit

    override fun onProviderEnabled(provider: String?) = Unit

    override fun onProviderDisabled(provider: String?) = Unit
}