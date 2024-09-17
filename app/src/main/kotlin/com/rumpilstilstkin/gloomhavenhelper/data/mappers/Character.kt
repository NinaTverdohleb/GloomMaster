package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterClassBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterForSave
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterInfo

fun CharacterForSave.toBd(teamId: Int) = CharacterBd(
    name = this.name,
    level = this.level,
    classId = this.classId,
    teamId = teamId
)

fun CharacterBd.toDomain(
    characterClassBd: CharacterClassBd
) = CharacterInfo(
    name = this.name,
    level = this.level,
    isAlive = this.isAlive,
    characterClass = characterClassBd.toDomain(),
    id = this.characterId
)