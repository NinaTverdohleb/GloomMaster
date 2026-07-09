package com.rumpilstilstkin.gloommaster.designsystem.icons

import com.rumpilstilstkin.gloommaster.designsystem.R

enum class ActionIcon(
    override val resId: Int,
) : GloomIcon {
    Trap(R.drawable.ic_trap),
    Fly(R.drawable.ic_fly),
    Range(R.drawable.ic_range),
    Attack(R.drawable.ic_attack),
    Heal(R.drawable.ic_heal),
    Retaliate(R.drawable.ic_retaliate),
    Shield(R.drawable.ic_shield),
    Move(R.drawable.ic_move),
    Loot(R.drawable.ic_loot),
}
