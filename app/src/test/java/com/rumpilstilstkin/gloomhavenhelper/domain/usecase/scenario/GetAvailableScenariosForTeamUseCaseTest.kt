package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import app.cash.turbine.test
import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LogicalCondition
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioInfoWithName
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEmpty

@OptIn(ExperimentalCoroutinesApi::class)
class GetAvailableScenariosForTeamUseCaseTest {
    private val teamRepository: TeamRepository = mockk()
    private val scenarioRepository: ScenarioRepository = mockk()
    private val localeRepository: LocaleRepository = mockk()

    @Test
    fun `given team and scenarios when invoked then excludes team scenarios`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = ShortTeamInfo.fixture(teamId = 7, packs = listOf(PackType.MAIN))
        val all = listOf(scenario(1), scenario(2), scenario(3))
        every { teamRepository.currentTeam } returns flowOf(team)
        every { localeRepository.observeLocaleUnic } returns flowOf("en")
        coEvery { scenarioRepository.getAllScenarios("en", listOf("MAIN")) } returns all
        every { scenarioRepository.getTeamScenariosFlow(7) } returns
            flowOf(listOf(com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioShortInfo.fixture(scenarioNumber = 2)))
        val sut = GetAvailableScenariosForTeamUseCase(teamRepository, scenarioRepository, localeRepository)

        // When / Then
        sut().test {
            expectThat(awaitItem().map { it.scenarioNumber }).containsExactly(1, 3)
            awaitComplete()
        }
    }

    @Test
    fun `given null current team when invoked then emits empty list`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(null)
        every { localeRepository.observeLocaleUnic } returns flowOf("en")
        val sut = GetAvailableScenariosForTeamUseCase(teamRepository, scenarioRepository, localeRepository)

        // When / Then
        sut().test {
            expectThat(awaitItem()).isEmpty()
            awaitComplete()
        }
    }

    private fun scenario(num: Int) =
        ScenarioInfoWithName(
            scenarioNumber = num,
            scenarioName = "Scenario $num",
            scenarioRequirements = LogicalCondition(""),
            newScenario = emptyList(),
            location = "Loc",
            pack = PackType.MAIN,
            monsters = emptyList(),
            isCompleted = false,
        )
}
