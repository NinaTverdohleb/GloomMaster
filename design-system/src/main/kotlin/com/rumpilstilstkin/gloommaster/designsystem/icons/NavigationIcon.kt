package com.rumpilstilstkin.gloommaster.designsystem.icons

import com.rumpilstilstkin.gloommaster.designsystem.R

enum class NavigationIcon(
    override val resId: Int,
) : GloomIcon {
    Team(R.drawable.design_system_ic_team),
    Character(R.drawable.design_system_ic_characters),
    Shop(R.drawable.design_system_ic_shop),
    Scenarios(R.drawable.design_system_ic_scenarios),
    CharacterGeneral(R.drawable.design_system_ic_ch_general),
    CharacterGoods(R.drawable.design_system_ic_ch_goods),
    CharacterPerks(R.drawable.design_system_ic_ch_percks),
    Close(R.drawable.design_system_ic_close),
}
