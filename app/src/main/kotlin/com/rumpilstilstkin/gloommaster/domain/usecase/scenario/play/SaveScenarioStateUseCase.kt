package com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play

import com.rumpilstilstkin.gloommaster.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloommaster.domain.entity.ScenarioGameState
import javax.inject.Inject

class SaveScenarioStateUseCase @Inject constructor(
    private val scenarioGameStateRepository: ScenarioGameStateRepository,
) {
    suspend operator fun invoke(state: ScenarioGameState) {
        scenarioGameStateRepository.save(state)
    }
}
