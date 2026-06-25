package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state

import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.MagicIcon
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
