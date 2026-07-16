package com.rumpilstilstkin.gloommaster.designsystem.icons

import com.rumpilstilstkin.gloommaster.designsystem.R

enum class GameStatIcons(
    override val resId: Int,
) : GloomIcon {
    Trap(R.drawable.design_system_ic_trap),
    Gold(R.drawable.design_system_ic_coins),
    Exp(R.drawable.design_system_ic_experience),
    Level(R.drawable.design_system_ic_level),
}
