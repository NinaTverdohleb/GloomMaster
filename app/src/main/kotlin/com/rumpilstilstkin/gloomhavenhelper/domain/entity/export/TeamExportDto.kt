package com.rumpilstilstkin.gloomhavenhelper.domain.entity.export

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterFullInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterPersonalQuest
import kotlinx.serialization.Serializable

@Serializable
data class TeamExportDto(
    val version: Int = CURRENT_VERSION,
    val team: ShortTeamInfo,
    val teamGoods: List<Int>,
    val unlockedClasses: List<String>,
    val teamScenarios: List<TeamScenarioDataDto>,
    val characters: List<CharacterDataDto>
) {
    companion object {
        const val CURRENT_VERSION = 1
    }
}

@Serializable
data class CharacterDataDto(
    val generalInfo: CharacterInfo,
    val personalQuest: CharacterPersonalQuest?,
    val goods: List<Int>,
    val perks: List<Int>,
)

@Serializable
data class TeamScenarioDataDto(
    val scenarioNumber: Int,
    val completed: Boolean
)
