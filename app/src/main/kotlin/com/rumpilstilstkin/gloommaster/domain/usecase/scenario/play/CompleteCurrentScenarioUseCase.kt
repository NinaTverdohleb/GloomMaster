package com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play

import com.rumpilstilstkin.gloommaster.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.CompleteScenarioUseCase
import javax.inject.Inject

class CompleteCurrentScenarioUseCase @Inject constructor(
    private val completeScenarioUseCase: CompleteScenarioUseCase,
    private val scenarioGameStateRepository: ScenarioGameStateRepository,
) {
    suspend operator fun invoke() {
        scenarioGameStateRepository.get()?.let { scenario ->
            val number = scenario.scenarioNumber
            if (number != null) {
                completeScenarioUseCase(number)
            } else {
                scenarioGameStateRepository.delete()
            }
        }
    }
}
