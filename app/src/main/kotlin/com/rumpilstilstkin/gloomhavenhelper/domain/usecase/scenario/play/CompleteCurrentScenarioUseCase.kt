package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.CompleteScenarioUseCase
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
