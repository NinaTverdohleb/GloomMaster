package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state

import androidx.compose.ui.graphics.Color
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Magic
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.GameIcon

fun Magic.toIcon(): GameIcon =
    when (this) {
        Magic.FIRE -> GameIcon.FIRE
        Magic.FROST -> GameIcon.FROST
        Magic.AIR -> GameIcon.AIR
        Magic.EARTH -> GameIcon.EARTH
        Magic.SUN -> GameIcon.SUN
        Magic.MOON -> GameIcon.MOON
    }

fun GameIcon.color(value: Int): Color =
    when (value) {
        0 -> color?.copy(alpha = 0.2f) ?: Color.White
        1 -> color?.copy(alpha = 0.5f) ?: Color.White
        else -> color ?: Color.White
    }

fun getChargeImage(value: Int?): Int? =
    when (value) {
        0 -> null
        1 -> R.drawable.ic_magic_half
        2 -> R.drawable.ic_magic_full
        else -> null
    }
