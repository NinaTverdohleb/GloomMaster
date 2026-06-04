package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods

import com.rumpilstilstkin.gloomhavenhelper.data.GoodsRepository
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddGoodForCharacterUseCaseTest {
    private val goodsRepository: GoodsRepository = mockk()

    @Test
    fun `given good ids and character id when invoked then addCharacterGoods is called once`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coJustRun { goodsRepository.addCharacterGoods(characterId = 5, goodIds = listOf(1, 2)) }
        val sut = AddGoodForCharacterUseCase(goodsRepository)

        // When
        sut(goodIds = listOf(1, 2), characterId = 5)

        // Then
        coVerify(exactly = 1) { goodsRepository.addCharacterGoods(characterId = 5, goodIds = listOf(1, 2)) }
    }
}
