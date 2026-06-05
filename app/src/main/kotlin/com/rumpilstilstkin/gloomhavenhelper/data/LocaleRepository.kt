package com.rumpilstilstkin.gloomhavenhelper.data

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.rumpilstilstkin.gloomhavenhelper.data.datasource.LocaleDatasource
import com.rumpilstilstkin.gloomhavenhelper.data.datasource.SystemLocaleDatasource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocaleRepository @Inject constructor(
    private val systemLocaleDataSource: SystemLocaleDatasource,
    private val appLocaleDataSource: LocaleDatasource,
) {
    val observeLocaleUnic: Flow<String> =
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

    fun getCurrentLocale(): String = appLocaleDataSource.locale ?: systemLocaleDataSource.getSystemLocale()

    fun setAppLocale(locale: String?) {
        appLocaleDataSource.locale = locale
    }

    val isLanguageDefault: Boolean
        get() {
            return appLocaleDataSource.locale == null
        }

    val avaliableLanguages = listOf("en", "ru")

    companion object {
        const val DEFAULT_LOCALE = "en"
    }
}
