package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
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
class UpdateNameForCurrentTeamUseCaseTest {
    private val teamRepository: TeamRepository = mockk()

    @Test
    fun `given new name when invoked then updates team`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = ShortTeamInfo.fixture(teamId = 7, teamName = "Old")
        every { teamRepository.currentTeam } returns flowOf(team)
        val captured = slot<ShortTeamInfo>()
        coJustRun { teamRepository.updateTeam(capture(captured)) }
        val sut = UpdateNameForCurrentTeamUseCase(teamRepository)

        // When
        sut("New")

        // Then
        expectThat(captured.captured.name).isEqualTo("New")
    }

    @Test
    fun `given same name when invoked then does nothing`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = ShortTeamInfo.fixture(teamName = "Same")
        every { teamRepository.currentTeam } returns flowOf(team)
        val sut = UpdateNameForCurrentTeamUseCase(teamRepository)

        // When
        sut("Same")

        // Then
        coVerify(exactly = 0) { teamRepository.updateTeam(any()) }
    }

    @Test
    fun `given null current team when invoked then does nothing`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(null)
        val sut = UpdateNameForCurrentTeamUseCase(teamRepository)

        // When
        sut("New")

        // Then
        coVerify(exactly = 0) { teamRepository.updateTeam(any()) }
    }
}
