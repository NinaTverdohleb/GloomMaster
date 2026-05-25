package com.rumpilstilstkin.gloomhavenhelper.localization

import android.content.Context
import android.content.res.Resources
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * App-wide reactive source of the active content locale ("en" / "ru"). Content repositories
 * combine [locale] into their streams so display text re-resolves the moment the user
 * switches language — without a re-seed and without losing progress.
 *
 * The persisted selection lives in [AppLocaleManager]; this holder mirrors the *resolved*
 * content locale and must be told to [refresh] after the selection changes (the static UI
 * is handled separately by re-creating the activity).
 */
@Singleton
class LocaleSource @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {
    private val _locale = MutableStateFlow(resolve())
    val locale: StateFlow<String> = _locale.asStateFlow()

    /** Current content locale snapshot, for suspend (non-stream) reads. */
    val current: String get() = _locale.value

    /** Re-derives the content locale from the persisted selection and system language. */
    fun refresh() {
        _locale.value = resolve()
    }

    private fun resolve(): String =
        SupportedLanguages.resolveContentLocale(
            languageTag = AppLocaleManager.currentLanguage(context).tag,
            systemLanguage = Resources.getSystem().configuration.locales[0].language,
        )
}
