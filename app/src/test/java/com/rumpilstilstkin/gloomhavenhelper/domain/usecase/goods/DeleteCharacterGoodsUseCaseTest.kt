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
class DeleteCharacterGoodsUseCaseTest {
    private val goodsRepository: GoodsRepository = mockk()

    @Test
    fun `given good id and character id when invoked then deleteCharacterGood is called once`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coJustRun { goodsRepository.deleteCharacterGood(goodId = 10, characterId = 5) }
        val sut = DeleteCharacterGoodsUseCase(goodsRepository)

        // When
        sut(goodId = 10, characterId = 5)

        // Then
        coVerify(exactly = 1) { goodsRepository.deleteCharacterGood(goodId = 10, characterId = 5) }
    }
}
