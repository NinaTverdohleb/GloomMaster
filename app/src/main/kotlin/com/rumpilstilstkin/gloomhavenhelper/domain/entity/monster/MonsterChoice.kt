package com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster

/**
 * A selectable monster in the scenario constructor. [name] holds the stable catalog key — the
 * identity used for selection, set math and persistence; [displayName] is the localized text shown
 * to the user. Keeping both apart lets the picker display the active language while the saved
 * scenario still references monsters by their language-independent key.
 */
data class MonsterChoice(
    val name: String,
    val displayName: String,
)
