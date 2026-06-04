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
class AddScenarioToTeamUseCaseTest {
    private val teamRepository: TeamRepository = mockk()
    private val scenarioRepository: ScenarioRepository = mockk()

    @Test
    fun `given current team when invoked then saves team scenario`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(ShortTeamInfo.fixture(teamId = 7))
        coJustRun { scenarioRepository.saveTeamScenario(42, 7) }
        val sut = AddScenarioToTeamUseCase(teamRepository, scenarioRepository)

        // When
        sut(42)

        // Then
        coVerify(exactly = 1) { scenarioRepository.saveTeamScenario(42, 7) }
    }

    @Test
    fun `given null current team when invoked then does nothing`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(null)
        val sut = AddScenarioToTeamUseCase(teamRepository, scenarioRepository)

        // When
        sut(42)

        // Then
        coVerify(exactly = 0) { scenarioRepository.saveTeamScenario(any(), any()) }
    }
}
