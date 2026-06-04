package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.DifficultyLevel
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateDifficultyLevelUseCaseTest {
    private val teamRepository: TeamRepository = mockk()

    @Test
    fun `given new difficulty when invoked then updates team`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = ShortTeamInfo.fixture(teamId = 7, difficultyLevel = DifficultyLevel.NORMAL)
        every { teamRepository.currentTeam } returns flowOf(team)
        val captured = slot<ShortTeamInfo>()
        coJustRun { teamRepository.updateTeam(capture(captured)) }
        val sut = UpdateDifficultyLevelUseCase(teamRepository)

        // When
        sut(DifficultyLevel.HARD)

        // Then
        expectThat(captured.captured.difficultyLevel).isEqualTo(DifficultyLevel.HARD)
    }

    @Test
    fun `given same difficulty when invoked then does nothing`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = ShortTeamInfo.fixture(difficultyLevel = DifficultyLevel.HARD)
        every { teamRepository.currentTeam } returns flowOf(team)
        val sut = UpdateDifficultyLevelUseCase(teamRepository)

        // When
        sut(DifficultyLevel.HARD)

        // Then
        coVerify(exactly = 0) { teamRepository.updateTeam(any()) }
    }

    @Test
    fun `given null current team when invoked then does nothing`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(null)
        val sut = UpdateDifficultyLevelUseCase(teamRepository)

        // When
        sut(DifficultyLevel.HARD)

        // Then
        coVerify(exactly = 0) { teamRepository.updateTeam(any()) }
    }
}
