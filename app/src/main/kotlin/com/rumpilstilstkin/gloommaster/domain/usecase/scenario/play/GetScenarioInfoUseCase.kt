package com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play

import com.rumpilstilstkin.gloommaster.data.LevelInfoRepository
import com.rumpilstilstkin.gloommaster.data.LocaleRepository
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.ScenarioBattleState
import com.rumpilstilstkin.gloommaster.domain.usecase.team.GetCurrentTeamUseCase
import com.rumpilstilstkin.gloommaster.utils.toResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetScenarioInfoUseCase @Inject constructor(
    private val getCurrentTeamUseCase: GetCurrentTeamUseCase,
    private val levelInfoRepository: LevelInfoRepository,
    private val restoreScenarioStateUseCase: RestoreScenarioStateUseCase,
    private val localeRepository: LocaleRepository,
) {
    suspend operator fun invoke(): Result<ScenarioBattleState> =
        withContext(Dispatchers.Default) {
            getCurrentTeamUseCase().first().let { team ->
                if (team != null) {
                    val levelInfo = levelInfoRepository.getLevelInfo(team.level).getOrNull()
                    restoreScenarioStateUseCase(team, levelInfo, localeRepository.getCurrentLocale()).toResult()
                } else {
                    Result.failure(IllegalStateException("Team is null"))
                }
            }
        }
}
