package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.data.LevelInfoRepository
import com.rumpilstilstkin.gloomhavenhelper.data.MonsterRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioBattleInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.utils.toResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetScenarioInfoUseCase @Inject constructor(
    private val monsterRepository: MonsterRepository,
    private val getCurrentTeamUseCase: GetCurrentTeamUseCase,
    private val levelInfoRepository: LevelInfoRepository,
) {
    suspend operator fun invoke(
        scenarioNumber: Int,
    ): Result<ScenarioBattleInfo> = withContext(Dispatchers.Default) {
        getCurrentTeamUseCase().first().let { team ->
            if (team != null) {
                val levelInfo = levelInfoRepository.getLevelInfo(team.level).getOrNull()
                val monsters = monsterRepository.getMonstersForScenario(
                    scenarioNumber = scenarioNumber,
                    level = team.level
                )
                ScenarioBattleInfo(
                    number = scenarioNumber,
                    name = team.activeScenario.firstOrNull { it.scenarioNumber == scenarioNumber }?.scenarioName
                        ?: "",
                    monsters = monsters,
                    golds = levelInfo?.goldCount ?: 0,
                    exp = levelInfo?.experience ?: 0,
                    trapDamage = levelInfo?.trapDamage ?: 0,
                    gamersCount = team.characters.size,
                    monsterLevel = levelInfo?.monsterLevel ?: 0
                ).toResult()
            } else {
                Result.failure(IllegalStateException("Team is null"))
            }
        }
    }
}