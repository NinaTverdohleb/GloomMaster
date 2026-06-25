package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state

import androidx.compose.ui.graphics.Color
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.MagicIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.game.GameIcon
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Magic

fun Magic.toIcon(): MagicIcon =
    when (this) {
        Magic.FIRE -> MagicIcon.Fire
        Magic.FROST -> MagicIcon.Frost
        Magic.AIR -> MagicIcon.Air
        Magic.EARTH -> MagicIcon.Earth
        Magic.SUN -> MagicIcon.Sun
        Magic.MOON -> MagicIcon.Moon
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
