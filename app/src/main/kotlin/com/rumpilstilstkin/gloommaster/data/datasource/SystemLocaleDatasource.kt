package com.rumpilstilstkin.gloommaster.data.datasource

import android.content.ComponentCallbacks2
import android.content.Context
import android.content.res.Configuration
import androidx.core.os.ConfigurationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SystemLocaleDatasource @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {
    fun getSystemLocale(): String {
        val configuration = context.resources.configuration
        val locales = ConfigurationCompat.getLocales(configuration)
        return if (!locales.isEmpty) {
            locales.get(0)?.language ?: Locale.getDefault().language
        } else {
            Locale.getDefault().language
        }
    }

    fun observeSystemLocale(): Flow<String> =
        callbackFlow {
            trySend(getSystemLocale())

            val callbacks =
                object : ComponentCallbacks2 {
                    override fun onConfigurationChanged(newConfig: Configuration) {
                        trySend(getSystemLocale())
                    }

                    @Deprecated("Deprecated in Java")
                    override fun onLowMemory() {}

                    override fun onTrimMemory(level: Int) {}
                }

            context.registerComponentCallbacks(callbacks)
            awaitClose { context.unregisterComponentCallbacks(callbacks) }
        }.distinctUntilChanged()
}
