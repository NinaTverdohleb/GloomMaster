package com.rumpilstilstkin.gloomhavenhelper.screens.models

import androidx.annotation.DrawableRes
import com.rumpilstilstkin.gloomhavenhelper.R

data class MonsterItem(
    val id: Int,
    val name: String,
    val currentCard: MonsterAbilityCard? = null,
    val units: List<MonsterUnit> = emptyList()
)

data class MonsterUnit(
    val number: Int,
    val currentLife: Int,
    val maxLife: Int,
    val stats: List<UnitStat>,
    val isSpecial: Boolean,
)

data class UnitStat(
    val type: ActionType,
    val value: Int
)

data class MonsterAbilityCard(
    val id: String,
    val name: String,
    val initiative: Int,
    val lines: List<CardLine>,
    val needsShuffle: Boolean = false
)

sealed interface CardLine {
    val subLine: CardLine?

    // Строка действия (например: "Move [Иконка] + 1")
    data class Action(
        val type: ActionType,
        val modifier: String,
        override val subLine: CardLine? = null
    ) : CardLine

    // Текстовое описание (например: "All adjacent enemies suffer 2 damage.")
    data class Text(val content: String) : CardLine {
        override val subLine: CardLine? = null
    }

    // Смешанная строка (например: "Teleport [Иконка] to the closest...")
    data class MixedText(
        val prefix: String,
        @DrawableRes val iconRes: Int,
        val suffix: String,
        override val subLine: CardLine? = null
    ) : CardLine
}

enum class ActionType(
    val title: String,
    @DrawableRes val iconRes: Int,
) {
    ATTACK(
        title = "Атака",
        iconRes = R.drawable.attack
    ),
    MOVE(
        title = "Движение",
        iconRes = R.drawable.move
    ),
    SHIELD(
        title = "Защита",
        iconRes = R.drawable.ic_shield
    ),
    RETALIATE(
        title = "Ответный удар",
        iconRes = R.drawable.retaliate
    ),
    STRENGTH(
        title = "Усиление",
        iconRes = R.drawable.strengthen
    ),
    POISON(
        title = "Отравление",
        iconRes = R.drawable.poison
    ),
    RANGED_ATTACK(
        title = "Дальний удар",
        iconRes = R.drawable.range
    )
}