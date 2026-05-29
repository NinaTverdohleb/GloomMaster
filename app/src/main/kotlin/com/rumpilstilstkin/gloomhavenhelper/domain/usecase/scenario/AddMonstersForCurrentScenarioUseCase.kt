package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.data.MonsterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioGameStateRepository
import javax.inject.Inject

class AddMonstersForCurrentScenarioUseCase @Inject constructor(
    private val scenarioGameStateRepository: ScenarioGameStateRepository,
    private val monsterRepository: MonsterRepository,
    private val localeRepository: LocaleRepository
) {
    suspend operator fun invoke(monsters: List<String>) {
        scenarioGameStateRepository.get()?.let { scenario ->
            val newCards = monsterRepository.getMonstersBySlugs(
                monsters,
                0,
                localeRepository.getCurrentLocale()
            ).flatMap { it.cards }
                .map { it.cardId }
            scenarioGameStateRepository.update(
                scenario.copy(
                    monsterNames = (scenario.monsterNames + monsters).distinct(),
                    availableCards = (scenario.availableCards + newCards).distinct()
                )
            )
        }
    }
}