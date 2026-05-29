package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.DifficultyLevel
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfoForSave

fun TeamInfoForSave.toBd() = TeamBd(
    name = this.name,
    packs = this.packs.map { it.name },
    difficultyLevel = this.difficultyLevel.value
)

fun TeamBd.toDomain(
    characterIds: List<Int>,
) = ShortTeamInfo(
    teamId = this.teamId,
    name = this.name,
    reputation = this.reputation,
    prosperity = this.prosperity,
    achievements = this.achievements,
    packs = this.packs.map { PackType.valueOf(it) },
    aliveCharacterIds = characterIds,
    churchValue = this.churchValue,
    difficultyLevel = DifficultyLevel.fromValue(this.difficultyLevel)
)

fun ShortTeamInfo.toBd() = TeamBd(
    teamId = this.teamId,
    name = this.name,
    achievements = this.achievements,
    reputation = this.reputation,
    prosperity = this.prosperity,
    packs = this.packs.map { it.name },
    churchValue = this.churchValue,
    difficultyLevel = this.difficultyLevel.value
)