package com.rumpilstilstkin.gloommaster.domain.usecase.goods

import app.cash.turbine.test
import com.rumpilstilstkin.gloommaster.data.GoodsRepository
import com.rumpilstilstkin.gloommaster.data.LocaleRepository
import com.rumpilstilstkin.gloommaster.domain.entity.Good
import com.rumpilstilstkin.gloommaster.domain.entity.GoodType
import com.rumpilstilstkin.gloommaster.domain.entity.PackType
import com.rumpilstilstkin.gloommaster.domain.usecase.goods.GetCharacterGoodsUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly

@OptIn(ExperimentalCoroutinesApi::class)
class GetCharacterGoodsUseCaseTest {
    private val goodsRepository: GoodsRepository = mockk()
    private val localeRepository: LocaleRepository = mockk()

    @Test
    fun `given current locale when invoked then forwards repo flow`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val goods = listOf(
            Good(id = 1, displayNumber = 1, name = "G1", type = GoodType.Body, cost = 10, image = "", pack = PackType.MAIN),
        )
        every { localeRepository.observeLocale } returns flowOf("ru")
        every { goodsRepository.getCharacterGoods(5, "ru") } returns flowOf(goods)
        val sut = GetCharacterGoodsUseCase(goodsRepository, localeRepository)

        // When / Then
        sut(5).test {
            expectThat(awaitItem()).containsExactly(goods.first())
            awaitComplete()
        }
    }
}
