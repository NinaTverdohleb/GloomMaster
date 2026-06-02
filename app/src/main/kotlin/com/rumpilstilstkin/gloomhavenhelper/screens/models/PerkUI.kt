package com.rumpilstilstkin.gloomhavenhelper.screens.models

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Perk

data class PerkUI(
    val id: Int,
    val text: String,
)

fun Perk.toUi() =
    PerkUI(
        id = this.id,
        text = this.text,
    )
