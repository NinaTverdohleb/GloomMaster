package com.rumpilstilstkin.gloomhavenhelper.ui

import androidx.compose.ui.graphics.vector.ImageVector
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.IconResCode
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.IconVectorCode
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.GloomhavenIcons
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.effects.Confuse
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.effects.Disarm
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.effects.Invisibility
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.effects.Next
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.effects.Paraliysis
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.effects.Poison
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.effects.Probitie
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.effects.Pull
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.effects.Push
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.effects.Rana
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.effects.Stun
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.effects.Target

fun IconVectorCode.toImage(): ImageVector = when(this) {
    IconVectorCode.NEXT -> GloomhavenIcons.Effect.Next
    IconVectorCode.PUSH -> GloomhavenIcons.Effect.Push
    IconVectorCode.PROBOY -> GloomhavenIcons.Effect.Probitie
    IconVectorCode.STUN -> GloomhavenIcons.Effect.Stun
    IconVectorCode.DISARM -> GloomhavenIcons.Effect.Disarm
    IconVectorCode.CONFUSE -> GloomhavenIcons.Effect.Confuse
    IconVectorCode.TARGET -> GloomhavenIcons.Effect.Target
    IconVectorCode.WOUND -> GloomhavenIcons.Effect.Rana
    IconVectorCode.PULL -> GloomhavenIcons.Effect.Pull
    IconVectorCode.PARALYZE -> GloomhavenIcons.Effect.Paraliysis
    IconVectorCode.INVISIBILITY -> GloomhavenIcons.Effect.Invisibility
    IconVectorCode.POISON -> GloomhavenIcons.Effect.Poison
}

fun IconResCode.toImage(): Int = when(this) {
    IconResCode.MINUS1 -> R.drawable.minus1
    IconResCode.MINUS2 -> R.drawable.minus2
    IconResCode.PLUS1 -> R.drawable.plus1
    IconResCode.PLUS2 -> R.drawable.plus2
    IconResCode.PLUS3 -> R.drawable.plus3
    IconResCode.PLUS4 -> R.drawable.plus4
    IconResCode.ZERO -> R.drawable.neutral
    IconResCode.FROST -> R.drawable.frost
    IconResCode.SUN -> R.drawable.sun
    IconResCode.MOON -> R.drawable.moon
    IconResCode.AIR -> R.drawable.air
    IconResCode.FIRE -> R.drawable.fire
    IconResCode.EARTH -> R.drawable.earth
}