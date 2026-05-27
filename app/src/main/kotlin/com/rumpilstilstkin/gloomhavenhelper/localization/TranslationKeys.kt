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

    // Achievements are identified by a stable catalog key everywhere — scenario unlock logic,
    // saved progress, and the translation store all use it. The display name lives only here.
    const val ACHIEVEMENT = "achievement"

    // Monsters are identified by a stable catalog key everywhere — scenario monster lists,
    // active-play game state, and the translation store. The display name lives only here.
    const val MONSTER = "monster"

    // Ability/special text embedded in monster stats, deduplicated and keyed by a stable catalog
    // key (see MonsterTextKeyIndex). This is the one place the catalog still carries inline Russian
    // content (it is not a "name"), so it is bridged by content at lookup time.
    const val MONSTER_TEXT = "monster_text"

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
