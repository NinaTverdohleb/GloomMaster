package com.rumpilstilstkin.gloommaster.data.mappers

import com.rumpilstilstkin.gloommaster.bd.entity.MonsterAbilityCardBd
import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterCardAction
import kotlinx.collections.immutable.toImmutableList

fun MonsterAbilityCardBd.toDomain(actions: List<MonsterCardAction>): MonsterCard =
    MonsterCard(
        cardId = cardId,
        actions = actions.toImmutableList(),
        needsShuffle = needsShuffle,
        deckName = deckName,
        initiative = initiative,
    )
