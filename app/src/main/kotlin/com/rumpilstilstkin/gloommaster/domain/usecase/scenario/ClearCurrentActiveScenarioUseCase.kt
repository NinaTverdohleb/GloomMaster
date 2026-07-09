package com.rumpilstilstkin.gloommaster.domain.usecase.scenario

import com.rumpilstilstkin.gloommaster.data.ScenarioGameStateRepository
import javax.inject.Inject

class ClearCurrentActiveScenarioUseCase @Inject constructor(
    private val scenarioGameStateRepository: ScenarioGameStateRepository,
) {
    suspend operator fun invoke() {
        scenarioGameStateRepository.delete()
    }
}
