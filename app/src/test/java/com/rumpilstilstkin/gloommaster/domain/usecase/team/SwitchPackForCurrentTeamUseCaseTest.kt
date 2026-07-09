package com.rumpilstilstkin.gloommaster.domain.usecase.team

import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.PackType
import com.rumpilstilstkin.gloommaster.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloommaster.domain.usecase.team.SwitchPackForCurrentTeamUseCase
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
import strikt.assertions.containsExactly
import strikt.assertions.doesNotContain

@OptIn(ExperimentalCoroutinesApi::class)
class SwitchPackForCurrentTeamUseCaseTest {
    private val teamRepository: TeamRepository = mockk()

    @Test
    fun `given pack not in team when invoked then pack is added`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = ShortTeamInfo.fixture(packs = listOf(PackType.MAIN))
        every { teamRepository.currentTeam } returns flowOf(team)
        val captured = slot<ShortTeamInfo>()
        coJustRun { teamRepository.updateTeam(capture(captured)) }
        val sut = SwitchPackForCurrentTeamUseCase(teamRepository)

        // When
        sut(PackType.FORGOTTEN_CIRCLES)

        // Then
        expectThat(captured.captured.packs).containsExactly(PackType.MAIN, PackType.FORGOTTEN_CIRCLES)
    }

    @Test
    fun `given pack already in team when invoked then pack is removed`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = ShortTeamInfo.fixture(packs = listOf(PackType.MAIN, PackType.FORGOTTEN_CIRCLES))
        every { teamRepository.currentTeam } returns flowOf(team)
        val captured = slot<ShortTeamInfo>()
        coJustRun { teamRepository.updateTeam(capture(captured)) }
        val sut = SwitchPackForCurrentTeamUseCase(teamRepository)

        // When
        sut(PackType.FORGOTTEN_CIRCLES)

        // Then
        expectThat(captured.captured.packs).doesNotContain(PackType.FORGOTTEN_CIRCLES)
        expectThat(captured.captured.packs).containsExactly(PackType.MAIN)
    }

    @Test
    fun `given null current team when invoked then does nothing`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(null)
        val sut = SwitchPackForCurrentTeamUseCase(teamRepository)

        // When
        sut(PackType.FORGOTTEN_CIRCLES)

        // Then
        coVerify(exactly = 0) { teamRepository.updateTeam(any()) }
    }
}
