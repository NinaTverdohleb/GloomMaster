package com.rumpilstilstkin.gloommaster.designsystem.icons

import com.rumpilstilstkin.gloommaster.designsystem.R

enum class LevelIcon(
    override val resId: Int,
) : GloomIcon {
    Easy(R.drawable.design_system_ic_level_easy),
    Normal(R.drawable.design_system_ic_level_normal),
    Hard(R.drawable.design_system_ic_level_hard),
    Hero(R.drawable.design_system_ic_level_hero),
}
