package com.rumpilstilstkin.gloomhavenhelper.data

import android.content.res.Resources
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.export.CharacterDataDto
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.export.TeamExportDto
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.export.TeamScenarioDataDto
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExportTeamRepository @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val teamRepository: TeamRepository,
    private val scenarioRepository: ScenarioRepository,
    private val goodsRepository: GoodsRepository,
    private val characterClassRepository: CharacterClassRepository,
    private val questsRepository: QuestsRepository,
) {
    suspend fun getExportTeamData(teamId: Int): Result<String> =
        teamRepository
            .getTeamInfo(teamId)
            ?.let { teamInfo ->
                val dto = TeamExportDto(
                    team = teamInfo,
                    teamGoods = goodsRepository.getTeamGoodsNumbers(teamId),
                    unlockedClasses = characterClassRepository.getAvailableClassesForTeamSync(teamId),

                    teamScenarios = scenarioRepository.getAllTeamScenarios(teamId)
                        .map {
                            TeamScenarioDataDto(
                                scenarioNumber = it.scenarioNumber,
                                completed = it.isCompleted
                            )
                        },
                    characters = characterRepository.getCharacterByTeamIdSync(teamId)
                        .map {
                            CharacterDataDto(
                                generalInfo = it,
                                personalQuest = questsRepository.getCharacterQuestById(it.id),
                                goods = goodsRepository.getCharacterGoodIds(it.id),
                                perks = characterRepository.getCharacterPerks(it.id)
                            )
                        }
                )
                Result.success(Json.encodeToString(dto))
            } ?: Result.failure(Resources.NotFoundException())
}