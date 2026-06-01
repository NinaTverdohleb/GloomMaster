package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

@Immutable
data class ScenarioStateUi(
    val name: String = "",
    val exp: Int = 0,
    val gold: Int = 0,
    val trapDamage: Int = 0,
    val level: Int = 0,
    val round: Int = 0,
    val availableEffects: Set<MonsterStatType> = MonsterStatType.mainEffectsPack,
    val monsters: ImmutableList<MonsterItem> = persistentListOf(),
    val monstersForAdd: ImmutableList<MonsterItem> = persistentListOf(),
    val magicChargeList: ImmutableMap<MagicUi, MagicValue> = persistentMapOf(),
)