package com.taskapiintegration.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPreference {
    private var vmSharedPreferences : SharedPreferences? = null
    private fun getSharedPreferencesInstance(context: Context): SharedPreferences {
        vmSharedPreferences = context.getSharedPreferences("com.taskapiintegration", Context.MODE_PRIVATE)
        return vmSharedPreferences!!
    }

    fun putString(context: Context, key: String, value: String) {
        vmSharedPreferences = getSharedPreferencesInstance(context)
        val editor = vmSharedPreferences!!.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(context: Context,key: String): String? {
        vmSharedPreferences = getSharedPreferencesInstance(context)
        return vmSharedPreferences?.getString(key,"")
    }
    fun remove(context: Context, string: String) {
        vmSharedPreferences = getSharedPreferencesInstance(context)
        val editor = vmSharedPreferences!!.edit()
        editor.remove(string)
            .apply()
    }
}