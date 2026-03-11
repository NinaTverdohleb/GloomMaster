package com.rumpilstilstkin.gloomhavenhelper.screens.models

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterAction
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class MonsterItem(
    val id: Int,
    val name: String,
    val isFly: Boolean,
    val currentCard: MonsterAbilityCard? = null,
    val units: ImmutableList<MonsterUnit> = persistentListOf(),
    val isBoss: Boolean = false,
) {
    companion object {
        fun fixture(
            id: Int = 1,
            name: String = "Name",
            isBoss: Boolean = false,
            isFly: Boolean = false,
        ) = MonsterItem(
            id = id,
            name = name,
            currentCard = null,
            isBoss = isBoss,
            isFly = isFly
        )
    }
}

@Serializable
@Immutable
data class MonsterUnit(
    val number: Int,
    val currentLife: Int,
    val maxLife: Int,
    val stats: ImmutableList<EffectItem>,
    val isSpecial: Boolean,
    val effects: ImmutableList<ActionUi> = persistentListOf(),
    val immunity: ImmutableList<ActionUi> = persistentListOf(),
    val level: Int,
) {
    companion object {
        fun create(
            monster: Monster,
            number: Int,
            isElite: Boolean,
            currentLife: Int? = null
        ): MonsterUnit {
            val maxLife = if (isElite) monster.eliteLife else monster.life
            val stats = if (isElite) monster.eliteStats else monster.stats
            return MonsterUnit(
                number = number,
                maxLife = maxLife,
                currentLife = currentLife ?: maxLife,
                stats = stats.map { EffectItem.fromCardAction(it) }.toImmutableList(),
                isSpecial = isElite,
                level = monster.level,
                immunity = monster.immunity.map { ActionUi.fromMonsterStatType(it) }.toImmutableList()
            )
        }

        fun createBoss(
            monster: Monster,
            gamersCount: Int
        ): MonsterUnit {
            val maxLife = monster.life * gamersCount
            return MonsterUnit(
                number = 1,
                maxLife = maxLife,
                currentLife = maxLife,
                stats = monster.stats.map { EffectItem.fromCardAction(it) }.toImmutableList(),
                isSpecial = false,
                level = monster.level,
                immunity = monster.immunity.map { ActionUi.fromMonsterStatType(it) }.toImmutableList()
            )
        }

        fun fixture(
            number: Int = 1
        ) = MonsterUnit(
            number = number,
            isSpecial = true,
            currentLife = 10,
            maxLife = 10,
            level = 1,
            stats = persistentListOf(
                EffectItem.Action(
                    type = ActionUi.MOVE,
                    modifier = "3"
                ),
                EffectItem.Action(
                    type = ActionUi.ATTACK,
                    modifier = "4"
                ),
                EffectItem.Action(
                    type = ActionUi.SHIELD,
                    modifier = "2"
                ),
            )
        )
    }
}

@Serializable
@Immutable
data class MonsterAbilityCard(
    val id: Int,
    val imageName: String,
    val needsShuffle: Boolean = false,
    val initiative: Int
) {
    val imagePath
        get() = "file:///android_asset/image/monster_cards/$imageName"

    companion object {
        fun createFromMonsterCard(card: MonsterCard) = MonsterAbilityCard(
            id = card.cardId,
            imageName = card.imageName,
            needsShuffle = card.needsShuffle,
            initiative = card.initiative
        )
    }
}

sealed interface EffectItem {
    val subLines: ImmutableList<EffectItem>?

    data class Action(
        val type: ActionUi,
        val modifier: String,
        override val subLines: ImmutableList<EffectItem>? = null
    ) : EffectItem

    data class Text(
        val content: String,
        override val subLines: ImmutableList<EffectItem>? = null
    ) : EffectItem

    companion object {
        fun fromCardAction(action: MonsterAction): EffectItem = when (action) {
            is MonsterAction.Action -> Action(
                type = ActionUi.fromMonsterStatType(action.statType),
                modifier = action.modifier,
                subLines = action.subAction?.let { action -> action.map { fromCardAction(it) } }?.toImmutableList()
            )

            is MonsterAction.Text -> Text(
                content = action.content,
                subLines = action.subAction?.let { action -> action.map { fromCardAction(it) } }?.toImmutableList()
            )
        }
    }
}