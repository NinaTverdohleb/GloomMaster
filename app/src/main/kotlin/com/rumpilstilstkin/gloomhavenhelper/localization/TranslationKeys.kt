package com.rumpilstilstkin.gloomhavenhelper.localization

/**
 * Shared identifiers for the localized-text store, used by both the seeding fillers and the
 * display-resolution mappers so writes and reads agree on the same keys.
 */
object TranslationKeys {
    const val SCENARIO = "scenario"
    const val GOOD = "good"
    const val QUEST = "quest"
    const val PERK = "perk"

    // Per-task quest text, keyed by "$questId:$taskId" so each task stays addressable
    // independent of the parent quest fields.
    const val QUEST_TASK = "quest_task"

    const val FIELD_NAME = "name"
    const val FIELD_LOCATION = "location"
    const val FIELD_TITLE = "title"
    const val FIELD_DESCRIPTION = "description"
    const val FIELD_SPECIAL = "special"
    const val FIELD_TEXT = "text"
}
