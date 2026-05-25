package com.rumpilstilstkin.gloomhavenhelper.localization

import androidx.annotation.StringRes
import com.rumpilstilstkin.gloomhavenhelper.R

/**
 * Languages the app can run in. [SYSTEM] follows the device locale (falling back to the
 * default English resources when the device language is unsupported); the rest force a
 * specific locale regardless of the system setting.
 */
enum class AppLanguage(
    val tag: String?,
    @param:StringRes val labelRes: Int,
) {
    SYSTEM(tag = null, labelRes = R.string.language_system),
    ENGLISH(tag = "en", labelRes = R.string.language_english),
    RUSSIAN(tag = "ru", labelRes = R.string.language_russian);

    companion object {
        fun fromTag(tag: String?): AppLanguage =
            entries.firstOrNull { it.tag == tag } ?: SYSTEM
    }
}
