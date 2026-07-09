package com.rumpilstilstkin.gloommaster.designsystem.icons

import com.rumpilstilstkin.gloommaster.designsystem.R

enum class EmptyIcon(
    override val resId: Int,
) : GloomIcon {
    Characters(R.drawable.ic_empty_characters),
    Goods(R.drawable.ic_empty_goods),
    Logo(R.drawable.ic_empty_logo),
    Perks(R.drawable.ic_empty_percks),
    Team(R.drawable.ic_empty_team),
    Enemy(R.drawable.ic_empty_enemy),
}
