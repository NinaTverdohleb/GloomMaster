package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.data.MonsterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetAvailableMonstersForTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val monsterRepository: MonsterRepository,
    private val scenarioGameStateRepository: ScenarioGameStateRepository
) {
    suspend operator fun invoke(): List<String> =
        teamRepository.currentTeam.first().let { team ->
            if (team == null) {
                emptyList()
            } else {
                val exclude = scenarioGameStateRepository.get()?.monsterSlugs ?: emptyList()
                monsterRepository.getMonstersForPacks(team.packs.map { it.name }).filter {
                    it !in exclude
                }
            }
        }
}
