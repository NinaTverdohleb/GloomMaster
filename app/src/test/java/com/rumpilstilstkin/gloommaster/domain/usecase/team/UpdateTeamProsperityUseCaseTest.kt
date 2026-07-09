package com.rumpilstilstkin.gloommaster.domain.usecase.team

import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloommaster.domain.usecase.goods.AddGoodsToTeamByNumbersUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.goods.GetGoodNumbersForLevelUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.team.GetTeamProsperityUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.team.UpdateTeamProsperityUseCase
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
class UpdateTeamProsperityUseCaseTest {
    private val teamRepository: TeamRepository = mockk()
    private val getTeamProsperityUseCase = GetTeamProsperityUseCase()
    private val getGoodsForLevelUseCase = GetGoodNumbersForLevelUseCase()
    private val addGoodsToTeamUseCase: AddGoodsToTeamByNumbersUseCase = mockk(relaxed = true)

    @Test
    fun `given different new value same level when invoked then prosperity is moved within level`() = runTest(UnconfinedTestDispatcher()) {
        // Given — prosperity=2 → level 1, value 2; new value 1
        // expected new source = 2 - 2 + 1 = 1
        val team = ShortTeamInfo.fixture(teamId = 7, prosperity = 2)
        every { teamRepository.currentTeam } returns flowOf(team)
        coJustRun { teamRepository.updateProsperity(7, 1) }
        val sut =
            UpdateTeamProsperityUseCase(
                teamRepository,
                getTeamProsperityUseCase,
                getGoodsForLevelUseCase,
                addGoodsToTeamUseCase,
            )

        // When
        sut(newProsperityLevelValue = 1)

        // Then
        coVerify(exactly = 1) { teamRepository.updateProsperity(7, 1) }
        coVerify(exactly = 0) { addGoodsToTeamUseCase(any(), any()) }
    }

    @Test
    fun `given same new value at non-zero when invoked then source plus 1 and may level up`() = runTest(UnconfinedTestDispatcher()) {
        // Given — prosperity=3 → level 1, value 3; new value 3 (same)
        // value != 0 → source+1 = 4 → new level 2 → adds level-2 goods
        val team = ShortTeamInfo.fixture(teamId = 7, prosperity = 3)
        every { teamRepository.currentTeam } returns flowOf(team)
        coJustRun { teamRepository.updateProsperity(7, 4) }
        val sut =
            UpdateTeamProsperityUseCase(
                teamRepository,
                getTeamProsperityUseCase,
                getGoodsForLevelUseCase,
                addGoodsToTeamUseCase,
            )

        // When
        sut(newProsperityLevelValue = 3)

        // Then — source +1; level rose 1→2 so add level-2 goods (15..21)
        coVerify(exactly = 1) { teamRepository.updateProsperity(7, 4) }
        coVerify(exactly = 1) { addGoodsToTeamUseCase(7, (15..21).toList()) }
    }

    @Test
    fun `given same new value at zero for non-start level when invoked then source minus 1 drops a level`() = runTest(UnconfinedTestDispatcher()) {
        // Given — prosperity=4 → level 2, value 0; new value 0 (same)
        // value == 0 → source-1 = 3 (drops to level 1, value 3)
        val team = ShortTeamInfo.fixture(teamId = 7, prosperity = 4)
        every { teamRepository.currentTeam } returns flowOf(team)
        coJustRun { teamRepository.updateProsperity(7, 3) }
        val sut =
            UpdateTeamProsperityUseCase(
                teamRepository,
                getTeamProsperityUseCase,
                getGoodsForLevelUseCase,
                addGoodsToTeamUseCase,
            )

        // When
        sut(newProsperityLevelValue = 0)

        // Then — new level not greater, no goods added
        coVerify(exactly = 1) { teamRepository.updateProsperity(7, 3) }
        coVerify(exactly = 0) { addGoodsToTeamUseCase(any(), any()) }
    }

    @Test
    fun `given prosperity at min and new value 0 when invoked then does nothing`() = runTest(UnconfinedTestDispatcher()) {
        // Given — prosperity 0 → level 1 value 0 → isStartValue true; new value 0 → bail
        val team = ShortTeamInfo.fixture(teamId = 7, prosperity = 0)
        every { teamRepository.currentTeam } returns flowOf(team)
        val sut =
            UpdateTeamProsperityUseCase(
                teamRepository,
                getTeamProsperityUseCase,
                getGoodsForLevelUseCase,
                addGoodsToTeamUseCase,
            )

        // When
        sut(newProsperityLevelValue = 0)

        // Then
        coVerify(exactly = 0) { teamRepository.updateProsperity(any(), any()) }
    }

    @Test
    fun `given prosperity at max when invoked then does nothing`() = runTest(UnconfinedTestDispatcher()) {
        // Given — prosperity 64 → level 9, isMax true
        val team = ShortTeamInfo.fixture(teamId = 7, prosperity = 64)
        every { teamRepository.currentTeam } returns flowOf(team)
        val sut =
            UpdateTeamProsperityUseCase(
                teamRepository,
                getTeamProsperityUseCase,
                getGoodsForLevelUseCase,
                addGoodsToTeamUseCase,
            )

        // When
        sut(newProsperityLevelValue = 5)

        // Then
        coVerify(exactly = 0) { teamRepository.updateProsperity(any(), any()) }
    }

    @Test
    fun `given null current team when invoked then does nothing`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(null)
        val sut =
            UpdateTeamProsperityUseCase(
                teamRepository,
                getTeamProsperityUseCase,
                getGoodsForLevelUseCase,
                addGoodsToTeamUseCase,
            )

        // When
        sut(newProsperityLevelValue = 1)

        // Then
        coVerify(exactly = 0) { teamRepository.updateProsperity(any(), any()) }
    }
}
