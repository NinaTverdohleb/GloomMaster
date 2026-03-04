package com.rumpilstilstkin.gloomhavenhelper.screens.models

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterAction
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

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

@Immutable
data class MonsterUnit(
    val number: Int,
    val currentLife: Int,
    val maxLife: Int,
    val stats: ImmutableList<EffectItem>,
    val isSpecial: Boolean,
    val effects: ImmutableList<ActionUi> = persistentListOf(),
    val immunity: ImmutableList<ActionUi> = persistentListOf()
){
    companion object {
        fun fixture(
            number: Int
        ) = MonsterUnit(
            number = number,
            isSpecial = true,
            currentLife = 10,
            maxLife = 10,
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

@Immutable
data class MonsterAbilityCard(
    val id: Int,
    val imageName: String,
    val needsShuffle: Boolean = false
) {
    val imagePath
        get() = "file:///android_asset/image/monster_cards/$imageName"

    companion object {
        fun createFromMonsterCard(card: MonsterCard) = MonsterAbilityCard(
            id = card.cardId,
            imageName = card.imageName,
            needsShuffle = card.needsShuffle
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