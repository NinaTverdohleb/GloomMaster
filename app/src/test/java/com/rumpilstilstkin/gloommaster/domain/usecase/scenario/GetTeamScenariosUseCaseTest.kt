package com.rumpilstilstkin.gloommaster.domain.usecase.scenario

import app.cash.turbine.test
import com.rumpilstilstkin.gloommaster.data.ScenarioRepository
import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloommaster.domain.entity.TeamScenarios
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.FilterTeamScenariosUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.GetTeamScenariosUseCase
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

@OptIn(ExperimentalCoroutinesApi::class)
class GetTeamScenariosUseCaseTest {
    private val teamRepository: TeamRepository = mockk()
    private val scenarioRepository: ScenarioRepository = mockk()
    private val filterTeamScenariosUseCase: FilterTeamScenariosUseCase = mockk()

    @Test
    fun `given current team when invoked then forwards filtered scenarios`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = ShortTeamInfo.fixture(teamId = 7)
        val expected =
            TeamScenarios(
                activeScenarios = emptyList(),
                blockedScenarios = emptyList(),
                completedScenarios = emptyList(),
            )
        every { teamRepository.currentTeam } returns flowOf(team)
        every { scenarioRepository.getTeamScenariosFlow(7) } returns flowOf(emptyList())
        coEvery { filterTeamScenariosUseCase(team.achievements, emptyList()) } returns expected
        val sut =
            GetTeamScenariosUseCase(teamRepository, scenarioRepository, filterTeamScenariosUseCase)

        // When / Then
        sut().test {
            expectThat(awaitItem()).isEqualTo(expected)
            awaitComplete()
        }
    }

    @Test
    fun `given null current team when invoked then emits empty TeamScenarios`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(null)
        val sut =
            GetTeamScenariosUseCase(teamRepository, scenarioRepository, filterTeamScenariosUseCase)

        // When / Then
        sut().test {
            val result = awaitItem()
            expectThat(result.activeScenarios).isEmpty()
            expectThat(result.blockedScenarios).isEmpty()
            expectThat(result.completedScenarios).isEmpty()
            awaitComplete()
        }
    }
}
