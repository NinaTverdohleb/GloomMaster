package com.rumpilstilstkin.gloomhavenhelper.ui

import androidx.compose.ui.graphics.vector.ImageVector
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.IconVectorCode
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.GloomhavenIcons
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.effects.Confuse
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.effects.Curse
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
    IconVectorCode.CURSE -> GloomhavenIcons.Effect.Curse
}