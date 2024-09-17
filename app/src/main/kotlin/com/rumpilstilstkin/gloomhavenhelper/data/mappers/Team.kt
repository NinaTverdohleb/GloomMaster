package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamWithScenariosBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfoForSave
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfoWithScenario

fun TeamInfoForSave.toBd() = TeamBd(
    name = this.name,
)

fun TeamWithScenariosBd.toDomain() = TeamInfoWithScenario(
    teamId = this.team.teamId,
    name = this.team.name,
    teamAchievement = this.team.teamAchievement,
    globalAchievement = this.team.globalAchievement,
    reputation = this.team.reputation,
    prosperity = this.team.prosperity,
    scenario = this.scenarios.map { it.toDomain() }
)

fun TeamBd.toDomain() = ShortTeamInfo(
    teamId = this.teamId,
    name = this.name,
    teamAchievement = this.teamAchievement.split(","),
    globalAchievement = this.globalAchievement.split(","),
    reputation = this.reputation,
    prosperity = this.prosperity,
)

fun ShortTeamInfo.toBd() = TeamBd(
    teamId = this.teamId,
    name = this.name,
    teamAchievement = this.teamAchievement.joinToString(","),
    globalAchievement = this.globalAchievement.joinToString(","),
    reputation = this.reputation,
    prosperity = this.prosperity,
)