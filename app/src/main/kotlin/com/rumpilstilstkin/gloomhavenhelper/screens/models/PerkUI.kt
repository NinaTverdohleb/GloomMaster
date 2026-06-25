package com.rumpilstilstkin.gloomhavenhelper.screens.models

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Perk

data class PerkUI(
    val id: Int,
    val text: String,
) {
    companion object {
        fun fixture(
            id: Int = 1,
            text: String = "Replace one card #01 with one card #03",
        ) = PerkUI(
            id = id,
            text = text,
        )
    }
}

fun Perk.toUi() =
    PerkUI(
        id = this.id,
        text = this.text,
    )
