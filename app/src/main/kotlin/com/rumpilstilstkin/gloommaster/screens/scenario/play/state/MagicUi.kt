package com.rumpilstilstkin.gloommaster.screens.scenario.play.state

import com.rumpilstilstkin.gloommaster.designsystem.icons.MagicIcon
import com.rumpilstilstkin.gloommaster.domain.entity.Magic

fun Magic.toIcon(): MagicIcon =
    when (this) {
        Magic.FIRE -> MagicIcon.Fire
        Magic.FROST -> MagicIcon.Frost
        Magic.AIR -> MagicIcon.Air
        Magic.EARTH -> MagicIcon.Earth
        Magic.SUN -> MagicIcon.Sun
        Magic.MOON -> MagicIcon.Moon
    }
