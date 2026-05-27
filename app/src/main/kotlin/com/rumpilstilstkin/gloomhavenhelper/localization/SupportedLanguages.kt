package com.rumpilstilstkin.gloomhavenhelper.localization

/**
 * Single registry of the languages the app ships content for. Derived from [AppLanguage] so
 * the picker, resource selection, content seeding and image lookup all agree on one list.
 * Adding a language means adding an [AppLanguage] entry plus its dictionaries.
 */
object SupportedLanguages {

    /** Content locales the translation store is seeded for, e.g. ["en", "ru"]. */
    val contentLocales: List<String> = AppLanguage.entries.mapNotNull { it.tag }

    /** Used when the system language is not one we ship content for. */
    const val DEFAULT = "en"

    /**
     * Locale whose card/goods artwork lives in the flat `image/<category>/` asset folders
     * (the original Russian art). Localized art for any other locale lives under
     * `image/<locale>/<category>/`; when a localized file is absent, image resolution falls
     * back to this locale's flat folder. See [LocalizedImageResolver].
     */
    const val FALLBACK_IMAGE_LOCALE = "ru"

    /**
     * Maps a persisted language selection to the content locale used for translation lookup.
     * [languageTag] is null for "follow system", in which case [systemLanguage] is mapped to
     * the supported set, defaulting to [DEFAULT] when unsupported.
     */
    fun resolveContentLocale(languageTag: String?, systemLanguage: String): String {
        val tag = languageTag ?: systemLanguage
        return if (tag in contentLocales) tag else DEFAULT
    }
}
