package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

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
) {

    suspend operator fun invoke(
        scenarioNumber: Int,
    ): Result<ScenarioBattleInfo> = withContext(Dispatchers.Default) {
        getCurrentTeamUseCase().first().let { team ->
            if (team != null) {
                val monsters = monsterRepository.getMonstersForScenario(
                    scenarioNumber = scenarioNumber,
                    level = team.level
                )
                ScenarioBattleInfo(
                    number = scenarioNumber,
                    name = team.activeScenario.firstOrNull { it.scenarioNumber == scenarioNumber }?.scenarioName
                        ?: "",
                    monsters = monsters,
                    golds = goldByLevel(team.level),
                    exp = expByLevel(team.level),
                    trapDamage = trapDamageByLevel(team.level),
                    gamersCount = team.characters.size
                ).toResult()
            } else {
                Result.failure(IllegalStateException("Team is null"))
            }
        }
    }

    private fun goldByLevel(level: Int): Int = when (level) {
        0, 1 -> 2
        2, 3 -> 3
        4, 5 -> 4
        6 -> 5
        else -> 6
    }

    private fun expByLevel(level: Int): Int = when (level) {
        0 -> 4
        1 -> 6
        2 -> 8
        3 -> 10
        4 -> 12
        5 -> 14
        6 -> 16
        else -> 18
    }

    private fun trapDamageByLevel(level: Int): Int = when {
        level <= 6 -> 2 + level
        else -> 9
    }
}