package com.rumpilstilstkin.gloommaster.data.mappers

import com.rumpilstilstkin.gloommaster.bd.entity.CharacterBd
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterForSave
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterInfo
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterShortInfo
import com.rumpilstilstkin.gloommaster.domain.entity.Team

fun CharacterForSave.toBd() =
    CharacterBd(
        name = this.name,
        level = this.level,
        characterType = this.characterType.name,
        teamId = this.teamId,
        experience = this.experience,
        goldCount = this.goldCount ?: (15 * (level + 1)),
        isAlive = this.isAlive,
        notes = this.notes,
        checkMarkCount = this.checkMarkCount,
        additionalContOfPerks = this.additionalContOfPerks,
    )

fun CharacterBd.toDomain(team: Team?) =
    CharacterInfo(
        name = this.name,
        level = this.level,
        isAlive = this.isAlive,
        characterType = CharacterClassType.valueOf(this.characterType),
        id = this.characterId,
        team = team,
        experience = this.experience,
        goldCount = this.goldCount,
        checkMarkCount = this.checkMarkCount,
        notes = this.notes,
        additionalContOfPerks = this.additionalContOfPerks,
    )

fun CharacterBd.toShortDomain() =
    CharacterShortInfo(
        name = this.name,
        level = this.level,
        isAlive = this.isAlive,
        characterType = CharacterClassType.valueOf(this.characterType),
        id = this.characterId,
        teamId = this.teamId,
        experience = this.experience,
        goldCount = this.goldCount,
        checkMarkCount = this.checkMarkCount,
        notes = this.notes,
    )
