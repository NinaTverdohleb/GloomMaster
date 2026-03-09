package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterForSave
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterShortInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.getExpForLevel

fun CharacterForSave.toBd() = CharacterBd(
    name = this.name,
    level = this.level,
    characterType = this.characterType.name,
    teamId = this.teamId,
    experience = this.experience,
    goldCount = 15*(level + 1)
)

fun CharacterBd.toDomain(
    team: ShortTeamInfo?
) = CharacterInfo(
    name = this.name,
    level = this.level,
    isAlive = this.isAlive,
    characterType = CharacterClassType.valueOf(this.characterType),
    id = this.characterId,
    team = team,
    experience = this.experience,
    goldCount = this.goldCount,
    checkMarkCount = this.checkMarkCount,
    notes = this.notes
)

fun CharacterBd.toShortDomain(
) = CharacterShortInfo(
    name = this.name,
    level = this.level,
    isAlive = this.isAlive,
    characterType = CharacterClassType.valueOf(this.characterType),
    id = this.characterId,
    teamId = this.teamId,
    experience = this.experience,
    goldCount = this.goldCount,
    checkMarkCount = this.checkMarkCount,
    notes = this.notes
)