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

    // Achievement translations are keyed by a stable catalog key (see AchievementKeyIndex), not
    // by the Russian name. Scenario unlock logic + saved progress still match by the canonical
    // (Russian) name; that name is bridged to the key at lookup time.
    const val ACHIEVEMENT = "achievement"

    // Monster names are keyed by a stable catalog key (see MonsterKeyIndex). The canonical
    // (Russian) name stays the game-logic/save identity (scenario monster lists, active-play
    // game state); that name is bridged to the key at lookup time.
    const val MONSTER = "monster"

    // Ability/special text embedded in monster stats, deduplicated and keyed by a stable catalog
    // key (see MonsterTextKeyIndex). The canonical Russian text is bridged to the key at lookup.
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
