package com.rumpilstilstkin.gloommaster.designsystem.icons

import com.rumpilstilstkin.gloommaster.designsystem.R

enum class NavigationIcon(
    override val resId: Int,
) : GloomIcon {
    Team(R.drawable.ic_team),
    Character(R.drawable.ic_characters),
    Shop(R.drawable.ic_shop),
    Scenarios(R.drawable.ic_scenarios),
    CharacterGeneral(R.drawable.ic_ch_general),
    CharacterGoods(R.drawable.ic_ch_goods),
    CharacterPerks(R.drawable.ic_ch_percks),
    Close(R.drawable.ic_close),
}
