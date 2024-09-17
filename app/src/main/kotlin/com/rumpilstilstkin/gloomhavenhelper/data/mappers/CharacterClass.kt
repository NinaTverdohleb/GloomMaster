package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterClassBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClass

fun CharacterClassBd.toDomain() = CharacterClass(
    id = this.characterClassId,
    image = this.image,
    name = this.name
)