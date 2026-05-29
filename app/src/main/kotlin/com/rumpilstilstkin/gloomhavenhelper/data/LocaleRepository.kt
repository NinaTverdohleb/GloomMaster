package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.data.datasource.LocaleDatasource
import com.rumpilstilstkin.gloomhavenhelper.data.datasource.SystemLocaleDatasource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocaleRepository @Inject constructor(
    private val systemLocaleDataSource: SystemLocaleDatasource,
    private val appLocaleDataSource: LocaleDatasource
) {

    val observeLocale: Flow<String> = combine(
        systemLocaleDataSource.observeSystemLocale(),
        appLocaleDataSource.observeAppLocale()
    ) { systemLocale, appLocale ->
        appLocale ?: systemLocale
    }.distinctUntilChanged()


    suspend fun getCurrentLocale(): String =
        appLocaleDataSource.locale ?: systemLocaleDataSource.getSystemLocale()

    suspend fun setAppLocale(locale: String?) {
        appLocaleDataSource.locale = locale
    }

    companion object {
        const val DEFAULT_LOCALE = "en"
    }
}