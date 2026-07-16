package com.rumpilstilstkin.gloommaster.designsystem.icons

import com.rumpilstilstkin.gloommaster.designsystem.R

enum class ActionIcon(
    override val resId: Int,
) : GloomIcon {
    Trap(R.drawable.design_system_ic_trap),
    Fly(R.drawable.design_system_ic_fly),
    Range(R.drawable.design_system_ic_range),
    Attack(R.drawable.design_system_ic_attack),
    Heal(R.drawable.design_system_ic_heal),
    Retaliate(R.drawable.design_system_ic_retaliate),
    Shield(R.drawable.design_system_ic_shield),
    Move(R.drawable.design_system_ic_move),
    Loot(R.drawable.design_system_ic_loot),
}
