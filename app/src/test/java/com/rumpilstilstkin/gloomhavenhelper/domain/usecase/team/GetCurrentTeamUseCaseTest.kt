package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import app.cash.turbine.test
import com.rumpilstilstkin.gloomhavenhelper.data.AchievementRepository
import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Achievement
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamScenarios
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.FilterTeamScenariosUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isNull

@OptIn(ExperimentalCoroutinesApi::class)
class GetCurrentTeamUseCaseTest {
    private val teamRepository: TeamRepository = mockk()
    private val characterRepository: CharacterRepository = mockk()
    private val getDiscountByReputation: GetDiscountByReputationUseCase = GetDiscountByReputationUseCase()
    private val getTeamProsperityUseCase: GetTeamProsperityUseCase = GetTeamProsperityUseCase()
    private val filterTeamScenariosUseCase: FilterTeamScenariosUseCase = mockk()
    private val scenarioGameStateRepository: ScenarioGameStateRepository = mockk()
    private val scenarioRepository: ScenarioRepository = mockk()
    private val achievementRepository: AchievementRepository = mockk()
    private val localeRepository: LocaleRepository = mockk()

    @Test
    fun `given team and dependencies when invoked then emits assembled TeamInfo`() = runTest(UnconfinedTestDispatcher()) {
        // Given — reputation 10 → discount -2; prosperity 20 → level 4 / value 5
        val team =
            ShortTeamInfo.fixture(teamId = 7, reputation = 10, prosperity = 20).copy(
                aliveCharacterIds = emptyList(),
                achievements =
                    listOf(
                        Achievement(slug = "a1", value = 1, maxValue = 1, isGlobal = false),
                        Achievement(slug = "a2", value = 1, maxValue = 1, isGlobal = true),
                    ),
            )
        every { teamRepository.currentTeam } returns flowOf(team)
        every { localeRepository.observeLocale } returns flowOf("en")
        coEvery { achievementRepository.getAchievementsNameBySlugs(any(), "en") } returns
            mapOf("a1" to "Local A1", "a2" to "Global A2")
        every { characterRepository.getCharacterByTeamId(7) } returns flowOf(emptyList())
        every { scenarioRepository.getTeamScenariosFlow(7) } returns flowOf(emptyList())
        every { scenarioGameStateRepository.getFlow() } returns flowOf(null)
        coEvery { filterTeamScenariosUseCase(any(), any()) } returns
            TeamScenarios(
                activeScenarios = emptyList(),
                blockedScenarios = emptyList(),
                completedScenarios = emptyList(),
            )

        val sut =
            GetCurrentTeamUseCase(
                teamRepository,
                characterRepository,
                getDiscountByReputation,
                getTeamProsperityUseCase,
                filterTeamScenariosUseCase,
                scenarioGameStateRepository,
                scenarioRepository,
                achievementRepository,
                localeRepository,
            )

        // When / Then
        sut().test {
            val info = awaitItem()!!
            expectThat(info.id).isEqualTo(7)
            expectThat(info.shopDiscount).isEqualTo(-2)
            expectThat(info.prosperity.prosperityLevel).isEqualTo(4)
            expectThat(info.prosperity.prosperityLevelValue).isEqualTo(5)
            expectThat(info.teamAchievement.map { it.slug }).isEqualTo(listOf("a1"))
            expectThat(info.globalAchievement.map { it.slug }).isEqualTo(listOf("a2"))
            expectThat(info.activeScenario).isEmpty()
            expectThat(info.hasActiveScenario).isFalse()
            awaitComplete()
        }
    }

    @Test
    fun `given null current team when invoked then emits null`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(null)
        every { localeRepository.observeLocale } returns flowOf("en")
        val sut =
            GetCurrentTeamUseCase(
                teamRepository,
                characterRepository,
                getDiscountByReputation,
                getTeamProsperityUseCase,
                filterTeamScenariosUseCase,
                scenarioGameStateRepository,
                scenarioRepository,
                achievementRepository,
                localeRepository,
            )

        // When / Then
        sut().test {
            expectThat(awaitItem()).isNull()
            awaitComplete()
        }
    }
}
