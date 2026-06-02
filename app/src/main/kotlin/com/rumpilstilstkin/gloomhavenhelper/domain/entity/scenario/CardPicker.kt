package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard

fun interface CardPicker {
    fun pick(cards: List<MonsterCard>): MonsterCard

    companion object {
        val Random: CardPicker = CardPicker { it.random() }
    }
}
