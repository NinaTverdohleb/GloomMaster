package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.localization.TranslationKeys

/**
 * Resolves display text for one locale from a pre-loaded snapshot of the translation store.
 *
 * Missing-text policy: when the active locale has no entry, debug builds render a visible key
 * marker (so untranslated content is obvious on screen and easy to report), while release builds
 * fall back to [fallbackByKey] — the canonical Russian source — so production users never see a
 * raw key. The marker is only a last resort in release, when even the source is missing.
 */
class TextResolver(
    private val byKey: Map<String, String>,
    // Canonical Russian source snapshot, used as the release fallback when [byKey] lacks an entry.
    private val fallbackByKey: Map<String, String> = emptyMap(),
    // Canonical (Russian) embedded stats text -> stable catalog key. Stats text is the one place
    // the catalog still carries inline Russian content (it is not a "name"), so it is bridged by
    // content rather than by an id.
    private val monsterTextKeys: Map<String, String> = emptyMap(),
    // True in debug (surface the key marker on a miss); false in release (fall back to the source).
    private val markMissingKeys: Boolean = true,
) {
    fun resolve(entityType: String, entityKey: String, fieldName: String): String {
        val compositeKey = key(entityType, entityKey, fieldName)
        byKey[compositeKey]?.let { return it }
        if (markMissingKeys) return marker(entityType, entityKey, fieldName)
        return fallbackByKey[compositeKey] ?: marker(entityType, entityKey, fieldName)
    }

    private fun marker(entityType: String, entityKey: String, fieldName: String): String =
        "⟦$entityType:$entityKey:$fieldName⟧"

    /** Display name for an achievement identified by its stable catalog [key]. */
    fun resolveAchievement(key: String): String =
        resolve(TranslationKeys.ACHIEVEMENT, key, TranslationKeys.FIELD_NAME)

    /** Display name for a monster identified by its stable catalog [key]. */
    fun resolveMonster(key: String): String =
        resolve(TranslationKeys.MONSTER, key, TranslationKeys.FIELD_NAME)

    /**
     * Display text for ability/special text embedded in monster stats, identified by its canonical
     * (Russian) content. Content not present in the catalog falls back to the canonical content
     * itself (rather than a key marker), so unrecognized stats text still renders readably.
     */
    fun resolveMonsterText(canonicalContent: String): String {
        val catalogKey = monsterTextKeys[canonicalContent] ?: return canonicalContent
        return resolve(TranslationKeys.MONSTER_TEXT, catalogKey, TranslationKeys.FIELD_TEXT)
    }

    companion object {
        fun key(entityType: String, entityKey: String, fieldName: String): String =
            "$entityType|$entityKey|$fieldName"
    }
}
