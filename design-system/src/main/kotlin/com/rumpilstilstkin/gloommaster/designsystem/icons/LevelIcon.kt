package com.rumpilstilstkin.gloommaster.designsystem.icons

import com.rumpilstilstkin.gloommaster.designsystem.R

enum class LevelIcon(
    override val resId: Int,
) : GloomIcon {
    Easy(R.drawable.ic_level_easy),
    Normal(R.drawable.ic_level_normal),
    Hard(R.drawable.ic_level_hard),
    Hero(R.drawable.ic_level_hero),
}
