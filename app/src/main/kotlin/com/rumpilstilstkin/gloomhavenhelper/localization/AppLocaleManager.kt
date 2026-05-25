package com.rumpilstilstkin.gloomhavenhelper.localization

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import androidx.core.content.edit
import java.util.Locale

/**
 * Single source of truth for the chosen [AppLanguage]. Reads/writes the selection from the
 * shared preferences file and applies it to a [Context]. Works without Hilt so it can be
 * called from [android.app.Activity.attachBaseContext], before injection is available.
 */
object AppLocaleManager {

    const val PREFS_NAME = "GlHelperPreferences"
    private const val KEY_LANGUAGE_TAG = "app_language_tag"

    fun currentLanguage(context: Context): AppLanguage =
        AppLanguage.fromTag(prefs(context).getString(KEY_LANGUAGE_TAG, null))

    fun setLanguage(context: Context, language: AppLanguage) {
        prefs(context).edit(commit = true) {
            if (language.tag == null) remove(KEY_LANGUAGE_TAG)
            else putString(KEY_LANGUAGE_TAG, language.tag)
        }
    }

    /**
     * Returns [base] reconfigured with the persisted language so resources resolve in the
     * chosen locale. For [AppLanguage.SYSTEM] the device locale is used. Also aligns the JVM
     * default locale so number/date formatting follows the active language.
     */
    fun wrap(base: Context): Context {
        val locale = currentLanguage(base).tag
            ?.let { Locale.forLanguageTag(it) }
            ?: systemLocale()
        Locale.setDefault(locale)
        val config = Configuration(base.resources.configuration).apply { setLocale(locale) }
        return base.createConfigurationContext(config)
    }

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private fun systemLocale(): Locale =
        Resources.getSystem().configuration.locales[0]
}
