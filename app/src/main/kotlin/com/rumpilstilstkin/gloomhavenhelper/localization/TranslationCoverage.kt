package com.rumpilstilstkin.gloomhavenhelper.localization

/**
 * Maintainer aid for verifying translation completeness (including Forgotten Circles content).
 *
 * Given the localized-text store flattened per locale — `locale -> (compositeKey -> text)` — it
 * reports the keys still untranslated for each locale. A key counts as untranslated for a locale
 * when it is missing or blank there but present (non-blank) for some other locale, i.e. measured
 * against the union of everything any shipped language can display. The dictionary coverage test
 * drives this to fail (and print the gaps) whenever a language falls behind.
 */
object TranslationCoverage {

    fun untranslatedByLocale(byLocale: Map<String, Map<String, String>>): Map<String, List<String>> {
        val expectedKeys: Set<String> = byLocale.values
            .flatMap { entries -> entries.filterValues { it.isNotBlank() }.keys }
            .toSet()

        return byLocale.mapValues { (_, entries) ->
            expectedKeys.filter { entries[it].isNullOrBlank() }.sorted()
        }
    }
}
