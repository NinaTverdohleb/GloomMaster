package com.rumpilstilstkin.gloommaster.data.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocaleDatasource @Inject constructor(
    private val preference: SharedPreferences,
) {
    var locale: String?
        get() = preference.getString(APP_LOCALE_KEY, null)
        set(value) {
            value?.let { preference.edit { putString(APP_LOCALE_KEY, it) } }
                ?: preference.edit { remove(APP_LOCALE_KEY) }
        }

    fun observeAppLocale(): Flow<String?> =
        callbackFlow {
            trySend(locale)

            val listener =
                SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                    if (key == APP_LOCALE_KEY) {
                        trySend(locale)
                    }
                }

            preference.registerOnSharedPreferenceChangeListener(listener)

            awaitClose {
                preference.unregisterOnSharedPreferenceChangeListener(listener)
            }
        }.distinctUntilChanged()

    companion object {
        private const val APP_LOCALE_KEY = "appLocaleKey"
    }
}
