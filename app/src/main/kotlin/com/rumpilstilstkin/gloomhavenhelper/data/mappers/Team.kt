package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioShortInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfoForSave
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfoWithScenario

fun TeamInfoForSave.toBd() = TeamBd(
    name = this.name,
    packs = this.packs.map { it.name }
)

fun TeamBd.toDomain(
    scenarios: List<ScenarioShortInfo>
) = TeamInfoWithScenario(
    teamId = this.teamId,
    name = this.name,
    teamAchievement = this.teamAchievement,
    globalAchievement = this.globalAchievement,
    reputation = this.reputation,
    prosperity = this.prosperity,
    scenario = scenarios,
    packs = this.packs.map { PackType.valueOf(it) },
    churchValue = this.churchValue
)

fun TeamBd.toDomain(
    characterIds: List<Int>
) = ShortTeamInfo(
    teamId = this.teamId,
    name = this.name,
    teamAchievement = this.teamAchievement,
    globalAchievement = this.globalAchievement,
    reputation = this.reputation,
    prosperity = this.prosperity,
    packs = this.packs.map { PackType.valueOf(it) },
    aliveCharacterIds = characterIds,
    churchValue = this.churchValue
)

fun ShortTeamInfo.toBd() = TeamBd(
    teamId = this.teamId,
    name = this.name,
    teamAchievement = this.teamAchievement,
    globalAchievement = this.globalAchievement,
    reputation = this.reputation,
    prosperity = this.prosperity,
    packs = this.packs.map { it.name },
    churchValue = this.churchValue
)