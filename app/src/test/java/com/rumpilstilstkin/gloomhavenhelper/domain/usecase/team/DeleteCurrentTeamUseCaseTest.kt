package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

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
class DeleteCurrentTeamUseCaseTest {
    private val teamRepository: TeamRepository = mockk()

    @Test
    fun `given current team when invoked then deletes that team`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = ShortTeamInfo.fixture(teamId = 7)
        every { teamRepository.currentTeam } returns flowOf(team)
        coJustRun { teamRepository.deleteTeam(team) }
        val sut = DeleteCurrentTeamUseCase(teamRepository)

        // When
        sut()

        // Then
        coVerify(exactly = 1) { teamRepository.deleteTeam(team) }
    }

    @Test
    fun `given null current team when invoked then does nothing`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(null)
        val sut = DeleteCurrentTeamUseCase(teamRepository)

        // When
        sut()

        // Then
        coVerify(exactly = 0) { teamRepository.deleteTeam(any()) }
    }
}
