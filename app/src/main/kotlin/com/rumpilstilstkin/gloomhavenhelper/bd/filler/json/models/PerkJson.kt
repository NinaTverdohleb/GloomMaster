package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models

import kotlinx.serialization.Serializable

@Serializable
data class CharacterPerksJson(
    val characterType: String,
    val perks: List<PerkJson>
)

@Serializable
data class PerkJson(
    // Empty in the canonical catalog (the entry only fixes the perk's position/count); populated
    // in the per-locale dictionaries, where the flattened 1-based index is the perk's id.
    val text: String = ""
)
