package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameState
import javax.inject.Inject

class SaveScenarioStateUseCase @Inject constructor(
    private val scenarioGameStateRepository: ScenarioGameStateRepository,
) {
    suspend operator fun invoke(state: ScenarioGameState) {
        scenarioGameStateRepository.save(state)
    }
}
