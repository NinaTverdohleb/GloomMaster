package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.data.MonsterRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.utils.toResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMonsterForScenarioUseCase @Inject constructor(
    private val monsterRepository: MonsterRepository,
    private val getCurrentTeamUseCase: GetCurrentTeamUseCase,
) {
    suspend operator fun invoke(scenarioNumber: Int): Result<List<String>> =
        withContext(Dispatchers.Default) {
            getCurrentTeamUseCase().first().let { team ->
                if (team != null) {
                    monsterRepository
                        .getMonsterSlugsForScenario(
                            scenarioNumber = scenarioNumber,
                        ).toResult()
                } else {
                    Result.failure(IllegalStateException("Team is null"))
                }
            }
        }
}
