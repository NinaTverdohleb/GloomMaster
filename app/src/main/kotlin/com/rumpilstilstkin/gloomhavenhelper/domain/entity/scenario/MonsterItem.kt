package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterAction
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlin.String

@Immutable
data class MonsterItem(
    val slug: String,
    val name: String,
    val isFly: Boolean,
    val deck: String,
    val currentCard: MonsterCard? = null,
    val units: ImmutableList<MonsterUnit> = persistentListOf(),
    val isBoss: Boolean = false,
) {
    companion object {
        fun fixture(
            slug: String = "1",
            name: String = "Name",
            isBoss: Boolean = false,
            isFly: Boolean = false,
            deck: String = "boss",
        ) = MonsterItem(
            slug = slug,
            name = name,
            currentCard = null,
            isBoss = isBoss,
            isFly = isFly,
            deck = deck,
        )
    }
}

data class MonsterUnit(
    val number: Int,
    val currentLife: Int,
    val maxLife: Int,
    val stats: ImmutableList<MonsterAction>,
    val isSpecial: Boolean,
    val effects: ImmutableList<MonsterStatType> = persistentListOf(),
    val immunity: ImmutableList<MonsterStatType> = persistentListOf(),
    val level: Int,
    val isNew: Boolean = true,
    val lifeMultiple: Boolean,
) {
    companion object {
        fun create(
            monster: Monster,
            number: Int,
            isElite: Boolean,
            currentLife: Int? = null,
            gamersCount: Int,
            effects: ImmutableList<MonsterStatType> = persistentListOf(),
            isNew: Boolean = true,
        ): MonsterUnit {
            val maxMonsterLife =
                if (isElite) {
                    monster.eliteLife
                } else if (monster.lifeMultiple) {
                    monster.life.times(gamersCount)
                } else {
                    monster.life
                }
            val stats = if (isElite) monster.eliteStats else monster.stats
            return MonsterUnit(
                number = number,
                maxLife = maxMonsterLife,
                currentLife = currentLife ?: maxMonsterLife,
                stats = stats.toImmutableList(),
                isSpecial = isElite,
                level = monster.level,
                effects = effects,
                immunity =
                    monster
                        .immunity
                        .map { it }
                        .toImmutableList(),
                isNew = isNew,
                lifeMultiple = monster.lifeMultiple,
            )
        }

        fun fixture(number: Int = 1) =
            MonsterUnit(
                number = number,
                isSpecial = true,
                currentLife = 10,
                maxLife = 10,
                level = 1,
                lifeMultiple = false,
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
            )
    }
}
