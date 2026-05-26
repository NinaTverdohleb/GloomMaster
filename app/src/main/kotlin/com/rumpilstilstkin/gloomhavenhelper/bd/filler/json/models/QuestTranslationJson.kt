package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models

import kotlinx.serialization.Serializable

/**
 * One personal quest's localized display text, keyed in the dictionary by quest id. Task texts
 * are keyed by task id within [tasks]. Logic fields (character class, task ids/counts) are never
 * translated, so only display strings live here.
 */
@Serializable
data class QuestTranslationJson(
    val title: String,
    val description: String = "",
    val special: String = "",
    val tasks: Map<String, String> = emptyMap(),
)
