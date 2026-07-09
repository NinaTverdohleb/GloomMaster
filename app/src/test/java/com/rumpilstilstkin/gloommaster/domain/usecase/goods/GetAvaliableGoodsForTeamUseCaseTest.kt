package com.rumpilstilstkin.gloommaster.domain.usecase.goods

import app.cash.turbine.test
import com.rumpilstilstkin.gloommaster.data.GoodsRepository
import com.rumpilstilstkin.gloommaster.data.LocaleRepository
import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.Good
import com.rumpilstilstkin.gloommaster.domain.entity.GoodType
import com.rumpilstilstkin.gloommaster.domain.entity.PackType
import com.rumpilstilstkin.gloommaster.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloommaster.domain.usecase.goods.GetAvaliableGoodsForTeamUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEmpty

@OptIn(ExperimentalCoroutinesApi::class)
class GetAvaliableGoodsForTeamUseCaseTest {
    private val teamRepository: TeamRepository = mockk()
    private val goodsRepository: GoodsRepository = mockk()
    private val localeRepository: LocaleRepository = mockk()

    @Test
    fun `given team's goods subset of all pack goods when invoked then returns the difference`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = ShortTeamInfo.fixture(teamId = 5, packs = listOf(PackType.MAIN))
        val all = listOf(good(1), good(2), good(3))
        val owned = listOf(good(2))
        every { localeRepository.observeLocale } returns flowOf("en")
        every { teamRepository.currentTeam } returns flowOf(team)
        coEvery { goodsRepository.getGoods(setOf(PackType.MAIN), "en") } returns all
        every { goodsRepository.getGoodsForTeam(5, "en") } returns flowOf(owned)
        val sut = GetAvaliableGoodsForTeamUseCase(teamRepository, goodsRepository, localeRepository)

        // When / Then
        sut().test {
            expectThat(awaitItem()).containsExactly(good(1), good(3))
            awaitComplete()
        }
    }

    @Test
    fun `given null team when invoked then returns empty list`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { localeRepository.observeLocale } returns flowOf("en")
        every { teamRepository.currentTeam } returns flowOf(null)
        val sut = GetAvaliableGoodsForTeamUseCase(teamRepository, goodsRepository, localeRepository)

        // When / Then
        sut().test {
            expectThat(awaitItem()).isEmpty()
            awaitComplete()
        }
    }

    private fun good(id: Int) =
        Good(
            id = id,
            displayNumber = id,
            name = "Good $id",
            type = GoodType.Body,
            cost = id * 10,
            image = "img",
            characterGoodId = null,
            pack = PackType.MAIN,
        )
}
