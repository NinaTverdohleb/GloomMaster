package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterAction
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterUnit
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.collections.immutable.toImmutableSet

@Immutable
data class MonsterUnitUi(
    val number: Int,
    val currentLife: Int,
    val maxLife: Int,
    val stats: ImmutableList<MonsterAction>,
    val isSpecial: Boolean,
    val effects: ImmutableMap<MonsterStatType, Boolean> = persistentMapOf(),
    val immunity: ImmutableSet<MonsterStatType> = persistentSetOf(),
    val level: Int,
    val isNew: Boolean = true,
) {
    companion object {
        fun fixture(
            number: Int = 1,
            isSpecial: Boolean = false,
        ) = MonsterUnitUi(
            number = number,
            isSpecial = isSpecial,
            currentLife = 10,
            maxLife = 10,
            level = 1,
            immunity = persistentSetOf(MonsterStatType.POISON),
            stats =
                persistentListOf(
                    MonsterAction.Action(
                        statType = MonsterStatType.MOVE,
                        modifier = "3",
                    ),
                    MonsterAction.Action(
                        statType = MonsterStatType.ATTACK,
                        modifier = "4",
                    ),
                    MonsterAction.Action(
                        statType = MonsterStatType.SHIELD,
                        modifier = "2",
                    ),
                    MonsterAction.Action(
                        statType = MonsterStatType.POISON,
                        modifier = "",
                    ),
                ),
            effects =
                (MonsterStatType.mainEffectsPack + MonsterStatType.fcEffectsPack)
                    .associateWith { false }
                    .toImmutableMap(),
        )
    }
}

fun MonsterUnit.toUi() =
    MonsterUnitUi(
        number = number,
        currentLife = currentLife,
        maxLife = maxLife,
        stats = stats.toImmutableList(),
        isSpecial = isSpecial,
        effects = effects.toImmutableMap(),
        immunity = immunity.toImmutableSet(),
        level = level,
        isNew = isNew,
    )
