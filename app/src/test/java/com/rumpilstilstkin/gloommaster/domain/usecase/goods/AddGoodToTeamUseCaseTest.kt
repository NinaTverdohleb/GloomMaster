package com.rumpilstilstkin.gloommaster.domain.usecase.goods

import com.rumpilstilstkin.gloommaster.data.GoodsRepository
import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloommaster.domain.usecase.goods.AddGoodToTeamUseCase
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
class AddGoodToTeamUseCaseTest {
    private val teamRepository: TeamRepository = mockk()
    private val goodsRepository: GoodsRepository = mockk()

    @Test
    fun `given current team when invoked then addGoodsToTeam is called with team id`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = ShortTeamInfo.fixture(teamId = 7)
        every { teamRepository.currentTeam } returns flowOf(team)
        coJustRun { goodsRepository.addGoodsToTeam(7, listOf(10, 20)) }
        val sut = AddGoodToTeamUseCase(teamRepository, goodsRepository)

        // When
        sut(goodId = listOf(10, 20))

        // Then
        coVerify(exactly = 1) { goodsRepository.addGoodsToTeam(7, listOf(10, 20)) }
    }
}
