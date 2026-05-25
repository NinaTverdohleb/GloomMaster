package com.rumpilstilstkin.gloomhavenhelper.data

/**
 * Resolves display text for one locale from a pre-loaded snapshot of the translation store.
 * A missing entry renders a visible key marker (rather than a blank) so untranslated content
 * is obvious on screen and easy to report.
 */
class TextResolver(
    private val byKey: Map<String, String>,
) {
    fun resolve(entityType: String, entityKey: String, fieldName: String): String =
        byKey[key(entityType, entityKey, fieldName)]
            ?: "⟦$entityType:$entityKey:$fieldName⟧"

    companion object {
        fun key(entityType: String, entityKey: String, fieldName: String): String =
            "$entityType|$entityKey|$fieldName"
    }
}
