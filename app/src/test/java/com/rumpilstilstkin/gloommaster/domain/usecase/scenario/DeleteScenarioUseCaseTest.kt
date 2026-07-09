package com.rumpilstilstkin.gloommaster.domain.usecase.scenario

import com.rumpilstilstkin.gloommaster.data.ScenarioRepository
import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.DeleteScenarioUseCase
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteScenarioUseCaseTest {
    private val teamRepository: TeamRepository = mockk()
    private val scenarioRepository: ScenarioRepository = mockk()

    @Test
    fun `given current team when invoked then deletes scenario`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(ShortTeamInfo.fixture(teamId = 7))
        coJustRun { scenarioRepository.deleteTeamScenario(teamId = 7, scenarioNumber = 42) }
        val sut = DeleteScenarioUseCase(teamRepository, scenarioRepository)

        // When
        sut(42)

        // Then
        coVerify(exactly = 1) { scenarioRepository.deleteTeamScenario(teamId = 7, scenarioNumber = 42) }
    }

    @Test
    fun `given null current team when invoked then does nothing`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(null)
        val sut = DeleteScenarioUseCase(teamRepository, scenarioRepository)

        // When
        sut(42)

        // Then
        coVerify(exactly = 0) { scenarioRepository.deleteTeamScenario(any(), any()) }
    }
}
