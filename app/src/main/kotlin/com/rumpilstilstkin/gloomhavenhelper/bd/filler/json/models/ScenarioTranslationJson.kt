package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models

import kotlinx.serialization.Serializable

/**
 * One scenario's localized display text, keyed in the dictionary by scenario number. Logic
 * fields are never translated, so only display strings live here.
 */
@Serializable
data class ScenarioTranslationJson(
    val name: String,
    val location: String = "",
)
