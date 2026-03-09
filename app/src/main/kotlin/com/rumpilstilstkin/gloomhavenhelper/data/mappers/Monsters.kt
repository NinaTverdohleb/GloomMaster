package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterAbilityCardBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard

fun MonsterAbilityCardBd.toDomain(): MonsterCard = MonsterCard(
    cardId = cardId,
    imageName = imageName,
    needsShuffle = needsShuffle,
    deckName = deckName
)