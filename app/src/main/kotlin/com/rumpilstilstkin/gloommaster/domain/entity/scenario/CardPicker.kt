package com.rumpilstilstkin.gloommaster.domain.entity.scenario

import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterCard

fun interface CardPicker {
    fun pick(cards: List<MonsterCard>): MonsterCard

    companion object {
        val Random: CardPicker = CardPicker { it.random() }
    }
}