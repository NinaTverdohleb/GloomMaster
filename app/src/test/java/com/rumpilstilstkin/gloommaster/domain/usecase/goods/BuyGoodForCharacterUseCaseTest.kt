package com.rumpilstilstkin.gloommaster.domain.usecase.goods

import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import com.rumpilstilstkin.gloommaster.data.GoodsRepository
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterShortInfo
import com.rumpilstilstkin.gloommaster.domain.usecase.goods.BuyGoodForCharacterUseCase
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BuyGoodForCharacterUseCaseTest {
    private val characterRepository: CharacterRepository = mockk()
    private val goodsRepository: GoodsRepository = mockk()

    @Test
    fun `given enough gold when invoked then character receives goods and gold is decremented`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val character = character(id = 5, goldCount = 50)
        coEvery { characterRepository.getCharacterById(5) } returns character
        coJustRun { goodsRepository.addCharacterGoods(characterId = 5, goodIds = listOf(1, 2)) }
        coJustRun { characterRepository.updateGold(5, 30) }
        val sut = BuyGoodForCharacterUseCase(characterRepository, goodsRepository)

        // When
        val result = sut(goodIds = listOf(1, 2), cost = 20, characterId = 5)

        // Then
        expectThat(result.isSuccess).isTrue()
        coVerifyOrder {
            goodsRepository.addCharacterGoods(characterId = 5, goodIds = listOf(1, 2))
            characterRepository.updateGold(5, 30)
        }
    }

    @Test
    fun `given insufficient gold when invoked then returns failure and no side effects`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coEvery { characterRepository.getCharacterById(5) } returns character(id = 5, goldCount = 10)
        val sut = BuyGoodForCharacterUseCase(characterRepository, goodsRepository)

        // When
        val result = sut(goodIds = listOf(1), cost = 20, characterId = 5)

        // Then
        expectThat(result.isFailure).isTrue()
        coVerify(exactly = 0) { goodsRepository.addCharacterGoods(any(), any()) }
        coVerify(exactly = 0) { characterRepository.updateGold(any(), any()) }
    }

    @Test
    fun `given character not found when invoked then returns failure`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coEvery { characterRepository.getCharacterById(999) } returns null
        val sut = BuyGoodForCharacterUseCase(characterRepository, goodsRepository)

        // When
        val result = sut(goodIds = listOf(1), cost = 5, characterId = 999)

        // Then
        expectThat(result.isFailure).isTrue()
    }

    @Test
    fun `given exactly enough gold when invoked then succeeds with gold reduced to zero`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coEvery { characterRepository.getCharacterById(5) } returns character(id = 5, goldCount = 20)
        coJustRun { goodsRepository.addCharacterGoods(characterId = 5, goodIds = listOf(1)) }
        coJustRun { characterRepository.updateGold(5, 0) }
        val sut = BuyGoodForCharacterUseCase(characterRepository, goodsRepository)

        // When
        val result = sut(goodIds = listOf(1), cost = 20, characterId = 5)

        // Then
        expectThat(result.isSuccess).isTrue()
        expectThat(result.isFailure).isFalse()
        coVerify(exactly = 1) { characterRepository.updateGold(5, 0) }
    }

    private fun character(
        id: Int,
        goldCount: Int,
    ) = CharacterShortInfo(
        name = "Brute",
        level = 1,
        characterType = CharacterClassType.Brute,
        isAlive = true,
        id = id,
        teamId = 1,
        experience = 0,
        goldCount = goldCount,
        checkMarkCount = 0,
        notes = "",
    )
}
