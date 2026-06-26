package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Magic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCardAction
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ChargeLevel
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterUnit
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

@Immutable
data class ScenarioStateUi(
    val scenarioNumber: Int? = null,
    val scenarioLocation: String? = null,
    val scenarioName: String = "",
    val exp: Int = 0,
    val gold: Int = 0,
    val trapDamage: Int = 0,
    val level: Int = 0,
    val round: Int = 0,
    val availableEffects: Set<MonsterStatType> = MonsterStatType.mainEffectsPack,
    val monstersForAdd: ImmutableList<MonsterItem> = persistentListOf(),
    val magicChargeList: ImmutableMap<Magic, ChargeLevel> = persistentMapOf(),
    val monsters: ImmutableList<MonsterItemUi> = persistentListOf(),
)

@Immutable
data class UnitCompact(
    val number: Int,
    val isSpecial: Boolean = false,
): Comparable<UnitCompact> {
    override fun compareTo(other: UnitCompact): Int {
        return compareValuesBy(
            this,
            other,
            UnitCompact::isSpecial,
            UnitCompact::number
        )
    }
}

@Immutable
data class MonsterItemUi(
    val slug: String,
    val name: String,
    val isFly: Boolean,
    val currentCard: MonsterCard? = null,
    val units: ImmutableMap<UnitCompact, MonsterUnit> = persistentMapOf(),
    val isBoss: Boolean = false,
){
    companion object {
        fun fixture(
            slug: String = "1",
            name: String = "Name",
            isBoss: Boolean = false,
            isFly: Boolean = false,
        ) = MonsterItemUi(
            slug = slug,
            name = name,
            currentCard = null,
            isBoss = isBoss,
            isFly = isFly,
        )
    }
}

