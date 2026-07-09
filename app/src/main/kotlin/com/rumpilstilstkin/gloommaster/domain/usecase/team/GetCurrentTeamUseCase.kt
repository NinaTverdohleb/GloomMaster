package com.rumpilstilstkin.gloommaster.domain.usecase.team

import com.rumpilstilstkin.gloommaster.data.AchievementRepository
import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import com.rumpilstilstkin.gloommaster.data.LocaleRepository
import com.rumpilstilstkin.gloommaster.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloommaster.data.ScenarioRepository
import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.TeamInfo
import com.rumpilstilstkin.gloommaster.domain.entity.toLevel
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.FilterTeamScenariosUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetCurrentTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val characterRepository: CharacterRepository,
    private val getDiscountByReputation: GetDiscountByReputationUseCase,
    private val getTeamProsperityUseCase: GetTeamProsperityUseCase,
    private val filterTeamScenariosUseCase: FilterTeamScenariosUseCase,
    private val scenarioGameStateRepository: ScenarioGameStateRepository,
    private val scenarioRepository: ScenarioRepository,
    private val achievementRepository: AchievementRepository,
    private val localeRepository: LocaleRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<TeamInfo?> =
        combine(
            teamRepository.currentTeam,
            localeRepository.observeLocale,
            ::Pair,
        ).flatMapLatest { (team, locale) ->

            if (team == null) return@flatMapLatest flowOf(null)
            val achievementsNames =
                achievementRepository.getAchievementsNameBySlugs(
                    team.achievements.map { it.slug },
                    locale,
                )

            combine(
                characterRepository.getCharacterByTeamId(team.teamId),
                scenarioRepository.getTeamScenariosFlow(team.teamId),
                scenarioGameStateRepository.getFlow(),
            ) { characters, scenarios, activeScenario ->
                val activeCharacters = characters.filter { it.isAlive }
                val teamScenarios = filterTeamScenariosUseCase(team.achievements, scenarios)

                TeamInfo(
                    id = team.teamId,
                    name = team.name,
                    level = activeCharacters.map { it.level }.toLevel(team.difficultyLevel),
                    teamAchievement =
                        team.achievements
                            .filter { !it.isGlobal }
                            .map { it.toAchievementWithName(achievementsNames[it.slug]) },
                    globalAchievement =
                        team.achievements
                            .filter { it.isGlobal }
                            .map { it.toAchievementWithName(achievementsNames[it.slug]) },
                    reputation = team.reputation,
                    prosperity = getTeamProsperityUseCase(team.prosperity),
                    activeScenario = teamScenarios.activeScenarios,
                    aliveCharacters = activeCharacters,
                    shopDiscount = getDiscountByReputation(team.reputation),
                    packs = team.packs,
                    hasActiveScenario = activeScenario != null,
                    churchValue = team.churchValue,
                    difficultyLevel = team.difficultyLevel,
                )
            }
        }
}
