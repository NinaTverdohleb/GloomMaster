package com.rumpilstilstkin.gloommaster.designsystem.icons

import com.rumpilstilstkin.gloommaster.designsystem.R

enum class GameStatIcons(
    override val resId: Int,
) : GloomIcon {
    Trap(R.drawable.ic_trap),
    Gold(R.drawable.ic_coins),
    Exp(R.drawable.ic_experience),
    Level(R.drawable.ic_level),
}
