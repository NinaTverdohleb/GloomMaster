package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.data.MonsterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetAvailableMonstersForTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val monsterRepository: MonsterRepository,
    private val scenarioGameStateRepository: ScenarioGameStateRepository,
    private val localeRepository: LocaleRepository,
) {
    suspend operator fun invoke(): Map<String, String> =
        teamRepository.currentTeam.first().let { team ->
            if (team == null) {
                emptyMap()
            } else {
                val locale = localeRepository.getCurrentLocale()
                val exclude = scenarioGameStateRepository.get()?.monsterSlugs ?: emptyList()
                monsterRepository
                    .getMonstersForPacks(
                        packs = team.packs.map { it.name },
                        locale = locale,
                    ).filterKeys {
                        it !in exclude
                    }
            }
        }
}
