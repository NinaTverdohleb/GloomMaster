package com.rumpilstilstkin.gloommaster.data

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.rumpilstilstkin.gloommaster.data.datasource.LocaleDatasource
import com.rumpilstilstkin.gloommaster.data.datasource.SystemLocaleDatasource
import com.rumpilstilstkin.gloommaster.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocaleRepository @Inject constructor(
    private val systemLocaleDataSource: SystemLocaleDatasource,
    private val appLocaleDataSource: LocaleDatasource,
    @ApplicationScope externalScope: CoroutineScope,
) {
    val observeLocale: Flow<String> =
        appLocaleDataSource
            .observeAppLocale()
            .onEach { locale ->
                if (locale == null) {
                    AppCompatDelegate.setApplicationLocales(LocaleListCompat.getEmptyLocaleList())
                } else {
                    val appLocale = LocaleListCompat.forLanguageTags(locale)
                    AppCompatDelegate.setApplicationLocales(appLocale)
                }
            }.combine(systemLocaleDataSource.observeSystemLocale()) { appLocale, systemLocale ->
                appLocale ?: systemLocale
            }.distinctUntilChanged()
            .shareIn(
                scope = externalScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                replay = 1,
            )

    fun getCurrentLocale(): String = appLocaleDataSource.locale ?: systemLocaleDataSource.getSystemLocale()

    fun setAppLocale(locale: String?) {
        appLocaleDataSource.locale = locale
    }

    val isLanguageDefault: Boolean
        get() {
            return appLocaleDataSource.locale == null
        }

    val availableLanguages = listOf("en", "ru")

    companion object {
        const val DEFAULT_LOCALE = "en"
    }
}
