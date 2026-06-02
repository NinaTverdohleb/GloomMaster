package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioGameStateRepository
import javax.inject.Inject

class ClearCurrentActiveScenarioUseCase @Inject constructor(
    private val scenarioGameStateRepository: ScenarioGameStateRepository,
) {
    suspend operator fun invoke() {
        scenarioGameStateRepository.delete()
    }
}
