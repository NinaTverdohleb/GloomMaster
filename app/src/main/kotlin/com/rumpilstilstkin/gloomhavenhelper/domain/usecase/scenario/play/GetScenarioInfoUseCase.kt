package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.data.LevelInfoRepository
import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.utils.toResult
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
    suspend operator fun invoke(): Result<ScenarioBattleState> = withContext(Dispatchers.Default) {
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