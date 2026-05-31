package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterAbilityCardBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCardAction

fun MonsterAbilityCardBd.toDomain(actions: List<MonsterCardAction>): MonsterCard = MonsterCard(
    cardId = cardId,
    actions = actions,
    needsShuffle = needsShuffle,
    deckName = deckName,
    initiative = initiative
)