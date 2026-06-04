package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods

import com.rumpilstilstkin.gloomhavenhelper.data.GoodsRepository
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
class RemoveGoodFromCurrentTeamUseCaseTest {
    private val goodsRepository: GoodsRepository = mockk()
    private val teamRepository: TeamRepository = mockk()

    @Test
    fun `given current team when invoked then removeGoodFromTeam is called with team id`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = ShortTeamInfo.fixture(teamId = 7)
        every { teamRepository.currentTeam } returns flowOf(team)
        coJustRun { goodsRepository.removeGoodFromTeam(7, 42) }
        val sut = RemoveGoodFromCurrentTeamUseCase(goodsRepository, teamRepository)

        // When
        sut(42)

        // Then
        coVerify(exactly = 1) { goodsRepository.removeGoodFromTeam(7, 42) }
    }
}
