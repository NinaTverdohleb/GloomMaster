package com.rumpilstilstkin.gloommaster.domain.usecase.team

import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloommaster.domain.usecase.team.DonateUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.team.GetNextChurchValueUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.team.GetTeamProsperityUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.team.UpdateTeamProsperityUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DonateUseCaseTest {
    private val teamRepository: TeamRepository = mockk()
    private val getNextChurchValueUseCase = GetNextChurchValueUseCase()
    private val updateTeamProsperityUseCase: UpdateTeamProsperityUseCase = mockk(relaxed = true)
    private val getTeamProsperityUseCase = GetTeamProsperityUseCase()

    @Test
    fun `given donation reaches next church threshold when invoked then prosperity is bumped`() = runTest(UnconfinedTestDispatcher()) {
        // Given — old 140 → next threshold 150; donate returns 150 (match)
        // prosperity 0 → level 1, value 0; new value coerced to max 3
        val team = ShortTeamInfo.fixture(teamId = 7, prosperity = 0)
        every { teamRepository.currentTeam } returns flowOf(team)
        coEvery { teamRepository.donate(7) } returns 150
        val sut =
            DonateUseCase(
                teamRepository,
                getNextChurchValueUseCase,
                updateTeamProsperityUseCase,
                getTeamProsperityUseCase,
            )

        // When
        sut(oldChurchValue = 140)

        // Then — prosperity bumped from 0 to 1
        coVerify(exactly = 1) { updateTeamProsperityUseCase(1) }
    }

    @Test
    fun `given prosperity at level cap when invoked then bump is coerced`() = runTest(UnconfinedTestDispatcher()) {
        // Given — prosperity 3 → level 1, value 3, range 0..3; coerced stays 3
        val team = ShortTeamInfo.fixture(teamId = 7, prosperity = 3)
        every { teamRepository.currentTeam } returns flowOf(team)
        coEvery { teamRepository.donate(7) } returns 150
        val sut =
            DonateUseCase(
                teamRepository,
                getNextChurchValueUseCase,
                updateTeamProsperityUseCase,
                getTeamProsperityUseCase,
            )

        // When
        sut(oldChurchValue = 140)

        // Then — bumped to 4 then coerced to range.last = 3
        coVerify(exactly = 1) { updateTeamProsperityUseCase(3) }
    }

    @Test
    fun `given donation below next threshold when invoked then prosperity is not touched`() = runTest(UnconfinedTestDispatcher()) {
        // Given — donate returns 140 != next(140) = 150
        val team = ShortTeamInfo.fixture(teamId = 7, prosperity = 0)
        every { teamRepository.currentTeam } returns flowOf(team)
        coEvery { teamRepository.donate(7) } returns 140
        val sut =
            DonateUseCase(
                teamRepository,
                getNextChurchValueUseCase,
                updateTeamProsperityUseCase,
                getTeamProsperityUseCase,
            )

        // When
        sut(oldChurchValue = 140)

        // Then
        coVerify(exactly = 0) { updateTeamProsperityUseCase(any()) }
    }

    @Test
    fun `given null current team when invoked then does nothing`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(null)
        val sut =
            DonateUseCase(
                teamRepository,
                getNextChurchValueUseCase,
                updateTeamProsperityUseCase,
                getTeamProsperityUseCase,
            )

        // When
        sut(oldChurchValue = 100)

        // Then
        coVerify(exactly = 0) { teamRepository.donate(any()) }
        coVerify(exactly = 0) { updateTeamProsperityUseCase(any()) }
    }
}
