package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterAbilityCardBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCardAction
import kotlinx.collections.immutable.toImmutableList

fun MonsterAbilityCardBd.toDomain(actions: List<MonsterCardAction>): MonsterCard =
    MonsterCard(
        cardId = cardId,
        actions = actions.toImmutableList(),
        needsShuffle = needsShuffle,
        deckName = deckName,
        initiative = initiative,
    )
