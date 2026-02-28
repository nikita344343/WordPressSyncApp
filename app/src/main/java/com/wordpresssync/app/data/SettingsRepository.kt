package com.wordpresssync.app.data

import android.content.Context
import android.content.SharedPreferences

class SettingsRepository(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var siteBaseUrl: String?
        get() = prefs.getString(KEY_SITE_URL, null)?.takeIf { it.isNotBlank() } ?: DEFAULT_SITE_URL
        set(value) = prefs.edit().putString(KEY_SITE_URL, value?.trim()?.takeIf { it.isNotBlank() }).apply()

    fun getApiBaseUrl(): String? {
        val url = siteBaseUrl ?: return null
        return url.trimEnd('/') + "/wp-json"
    }

    companion object {
        private const val PREFS_NAME = "wordpress_sync"
        private const val KEY_SITE_URL = "site_base_url"
        private const val DEFAULT_SITE_URL = "https://appnbmedia.ru"
    }
}
