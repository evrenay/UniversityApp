package com.evren.core.utils

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.location.LocationManager
import android.provider.Settings
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

class ResourceProvider(val context: Context) {

    //TODO merge this class with ResourceUtil class

    fun getString(@StringRes resId: Int): String {
        return context.getString(resId)
    }

    fun getString(@StringRes resId: Int, value: String): String {
        return context.getString(resId, value)
    }

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }



    fun getStringArray(resId: Int): Array<String> {
        return context.resources.getStringArray(resId)
    }

    fun getColor(@ColorRes resId: Int): Int {
        return ContextCompat.getColor(context,resId)
    }


    fun getColorArray(resId: Int): TypedArray {
        return context.resources.obtainTypedArray(resId);
    }

    fun getDrawable(@DrawableRes resId: Int): Drawable? {
        return ContextCompat.getDrawable(context, resId)
    }

    fun getFont(@FontRes fontId : Int) = ResourcesCompat.getFont(context, fontId)


    fun checkPermission(permission : String): Boolean {
        return context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }


    fun isLocationEnabled(): Boolean {
        val locationManager =  context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }


}