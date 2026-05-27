package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.localization.TranslationKeys

/**
 * Resolves display text for one locale from a pre-loaded snapshot of the translation store.
 * A missing entry renders a visible key marker (rather than a blank) so untranslated content
 * is obvious on screen and easy to report.
 */
class TextResolver(
    private val byKey: Map<String, String>,
    // Canonical (Russian) achievement name -> stable catalog key. Achievements are keyed in the
    // store by this stable key, but callers only have the canonical name (the logic identity),
    // so [resolveAchievement] bridges name -> key before looking up the text.
    private val achievementKeys: Map<String, String> = emptyMap(),
    // Canonical (Russian) monster name -> stable catalog key (same bridge rationale as achievements).
    private val monsterKeys: Map<String, String> = emptyMap(),
    // Canonical (Russian) embedded stats text -> stable catalog key.
    private val monsterTextKeys: Map<String, String> = emptyMap(),
) {
    fun resolve(entityType: String, entityKey: String, fieldName: String): String =
        byKey[key(entityType, entityKey, fieldName)]
            ?: "⟦$entityType:$entityKey:$fieldName⟧"

    /** Display name for an achievement identified by its canonical (Russian) name. */
    fun resolveAchievement(canonicalName: String): String =
        resolve(
            TranslationKeys.ACHIEVEMENT,
            achievementKeys[canonicalName] ?: canonicalName,
            TranslationKeys.FIELD_NAME,
        )

    /** Display name for a monster identified by its canonical (Russian) name. */
    fun resolveMonster(canonicalName: String): String =
        resolve(
            TranslationKeys.MONSTER,
            monsterKeys[canonicalName] ?: canonicalName,
            TranslationKeys.FIELD_NAME,
        )

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
