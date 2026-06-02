package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models

import kotlinx.serialization.Serializable

@Serializable
data class CharacterPerksJson(
    val characterType: String,
    val perksCount: Int,
)

@Serializable
data class PerkTranslationGroupJson(
    val characterType: String,
    val perks: List<PerkTranslationItemJson>,
)

@Serializable
data class PerkTranslationItemJson(
    val id: Int,
    val text: String,
)
