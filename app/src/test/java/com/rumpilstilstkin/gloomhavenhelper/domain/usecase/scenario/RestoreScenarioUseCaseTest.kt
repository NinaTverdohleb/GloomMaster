package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
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
class RestoreScenarioUseCaseTest {
    private val teamRepository: TeamRepository = mockk()
    private val scenarioRepository: ScenarioRepository = mockk()

    @Test
    fun `given current team when invoked then restores scenario`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(ShortTeamInfo.fixture(teamId = 7))
        coJustRun { scenarioRepository.restoreTeamScenario(teamId = 7, scenarioNumber = 42) }
        val sut = RestoreScenarioUseCase(teamRepository, scenarioRepository)

        // When
        sut(42)

        // Then
        coVerify(exactly = 1) { scenarioRepository.restoreTeamScenario(teamId = 7, scenarioNumber = 42) }
    }

    @Test
    fun `given null current team when invoked then does nothing`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(null)
        val sut = RestoreScenarioUseCase(teamRepository, scenarioRepository)

        // When
        sut(42)

        // Then
        coVerify(exactly = 0) { scenarioRepository.restoreTeamScenario(any(), any()) }
    }
}
