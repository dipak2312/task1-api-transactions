package com.taskapiintegration.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object SecurePrefs {
    private fun getPrefs(context: Context) = EncryptedSharedPreferences.create(
        "secure_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveToken(context: Context, token: String) {
        getPrefs(context).edit().putString("token", token).apply()
    }

    fun getToken(context: Context): String? {
        return getPrefs(context).getString("token", null)
    }

    fun clearToken(context: Context) {
        getPrefs(context).edit().remove("token").apply()
    }
}
