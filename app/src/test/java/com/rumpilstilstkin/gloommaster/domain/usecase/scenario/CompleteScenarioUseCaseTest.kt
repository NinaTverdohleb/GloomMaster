package com.rumpilstilstkin.gloommaster.domain.usecase.scenario

import com.rumpilstilstkin.gloommaster.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloommaster.data.ScenarioRepository
import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.LogicalCondition
import com.rumpilstilstkin.gloommaster.domain.entity.PackType
import com.rumpilstilstkin.gloommaster.domain.entity.ScenarioInfo
import com.rumpilstilstkin.gloommaster.domain.entity.ScenarioShortInfo
import com.rumpilstilstkin.gloommaster.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.CompleteScenarioUseCase
import io.mockk.coEvery
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
class CompleteScenarioUseCaseTest {
    private val teamRepository: TeamRepository = mockk()
    private val scenarioRepository: ScenarioRepository = mockk()
    private val scenarioGameStateRepository: ScenarioGameStateRepository = mockk()

    @Test
    fun `given completed scenario unlocks new ones in same pack when invoked then they are saved`() = runTest(UnconfinedTestDispatcher()) {
        // Given — team has scenario 5 done; completing 5 unlocks 6 (new, MAIN pack) and 7 (new, FC pack — filtered out)
        val team = ShortTeamInfo.fixture(teamId = 7, packs = listOf(PackType.MAIN))
        every { teamRepository.currentTeam } returns flowOf(team)
        coEvery { scenarioRepository.getAllTeamScenarios(7) } returns
            listOf(ScenarioShortInfo.fixture(scenarioNumber = 5))
        coEvery { scenarioRepository.getShortScenario(5) } returns
            ScenarioInfo(
                scenarioNumber = 5,
                scenarioRequirements = LogicalCondition(""),
                newScenario = listOf(6, 7),
                pack = PackType.MAIN,
                monsters = emptyList(),
            )
        coEvery { scenarioRepository.getShortScenario(6) } returns
            ScenarioInfo(
                scenarioNumber = 6,
                scenarioRequirements = LogicalCondition(""),
                newScenario = emptyList(),
                pack = PackType.MAIN,
                monsters = emptyList(),
            )
        coEvery { scenarioRepository.getShortScenario(7) } returns
            ScenarioInfo(
                scenarioNumber = 7,
                scenarioRequirements = LogicalCondition(""),
                newScenario = emptyList(),
                pack = PackType.FORGOTTEN_CIRCLES,
                monsters = emptyList(),
            )
        coJustRun { scenarioRepository.completeTeamScenario(7, 5) }
        coJustRun { scenarioGameStateRepository.delete() }
        coJustRun { scenarioRepository.saveTeamScenario(6, 7) }
        val sut =
            CompleteScenarioUseCase(teamRepository, scenarioRepository, scenarioGameStateRepository)

        // When
        sut(5)

        // Then
        coVerify(exactly = 1) { scenarioRepository.completeTeamScenario(7, 5) }
        coVerify(exactly = 1) { scenarioGameStateRepository.delete() }
        coVerify(exactly = 1) { scenarioRepository.saveTeamScenario(6, 7) }
        coVerify(exactly = 0) { scenarioRepository.saveTeamScenario(7, 7) }
    }

    @Test
    fun `given null current team when invoked then does nothing`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(null)
        val sut =
            CompleteScenarioUseCase(teamRepository, scenarioRepository, scenarioGameStateRepository)

        // When
        sut(5)

        // Then
        coVerify(exactly = 0) { scenarioRepository.completeTeamScenario(any(), any()) }
        coVerify(exactly = 0) { scenarioGameStateRepository.delete() }
    }
}
