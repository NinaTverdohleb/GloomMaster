package com.rumpilstilstkin.gloommaster.domain.usecase.scenario

import com.rumpilstilstkin.gloommaster.data.LocaleRepository
import com.rumpilstilstkin.gloommaster.data.MonsterRepository
import com.rumpilstilstkin.gloommaster.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterName
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetAvailableMonstersForTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val monsterRepository: MonsterRepository,
    private val scenarioGameStateRepository: ScenarioGameStateRepository,
    private val localeRepository: LocaleRepository,
) {
    suspend operator fun invoke(): List<MonsterName> =
        teamRepository.currentTeam.first().let { team ->
            if (team == null) {
                emptyList()
            } else {
                val locale = localeRepository.getCurrentLocale()
                val exclude = scenarioGameStateRepository.get()?.monsterSlugs ?: emptyList()
                monsterRepository
                    .getMonstersForPacks(
                        packs = team.packs.map { it.name },
                        locale = locale,
                    ).filter {
                        it.slug !in exclude
                    }
            }
        }
}
