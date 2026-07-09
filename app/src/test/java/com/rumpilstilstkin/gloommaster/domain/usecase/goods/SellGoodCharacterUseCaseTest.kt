package com.rumpilstilstkin.gloommaster.domain.usecase.goods

import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import com.rumpilstilstkin.gloommaster.data.GoodsRepository
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterShortInfo
import com.rumpilstilstkin.gloommaster.domain.usecase.goods.DeleteCharacterGoodsUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.goods.SellGoodCharacterUseCase
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
class SellGoodCharacterUseCaseTest {
    private val characterRepository: CharacterRepository = mockk()
    private val goodsRepository: GoodsRepository = mockk()
    private val deleteCharacterGoodsUseCase = DeleteCharacterGoodsUseCase(goodsRepository)

    @Test
    fun `given existing character when invoked then good is deleted and half cost added to gold`() = runTest(UnconfinedTestDispatcher()) {
        // Given — cost=21 → half = 10 (integer div), goldCount=5 → updateGold to 15
        coEvery { characterRepository.getCharacterById(5) } returns character(5, goldCount = 5)
        coJustRun { goodsRepository.deleteCharacterGood(goodId = 99, characterId = 5) }
        coJustRun { characterRepository.updateGold(5, 15) }
        val sut = SellGoodCharacterUseCase(characterRepository, deleteCharacterGoodsUseCase)

        // When
        val result = sut(goodId = 99, characterId = 5, cost = 21)

        // Then
        expectThat(result.isSuccess).isTrue()
        coVerifyOrder {
            goodsRepository.deleteCharacterGood(goodId = 99, characterId = 5)
            characterRepository.updateGold(5, 15)
        }
    }

    @Test
    fun `given odd cost when invoked then half-cost rounds down`() = runTest(UnconfinedTestDispatcher()) {
        // Given — cost=5 → half=2 (integer div), goldCount=0 → updateGold to 2
        coEvery { characterRepository.getCharacterById(5) } returns character(5, goldCount = 0)
        coJustRun { goodsRepository.deleteCharacterGood(goodId = 99, characterId = 5) }
        coJustRun { characterRepository.updateGold(5, 2) }
        val sut = SellGoodCharacterUseCase(characterRepository, deleteCharacterGoodsUseCase)

        // When
        val result = sut(goodId = 99, characterId = 5, cost = 5)

        // Then
        expectThat(result.isSuccess).isTrue()
        coVerify(exactly = 1) { characterRepository.updateGold(5, 2) }
    }

    @Test
    fun `given character not found when invoked then returns failure and no side effects`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coEvery { characterRepository.getCharacterById(999) } returns null
        val sut = SellGoodCharacterUseCase(characterRepository, deleteCharacterGoodsUseCase)

        // When
        val result = sut(goodId = 99, characterId = 999, cost = 10)

        // Then
        expectThat(result.isFailure).isTrue()
        expectThat(result.isSuccess).isFalse()
        coVerify(exactly = 0) { goodsRepository.deleteCharacterGood(any(), any()) }
        coVerify(exactly = 0) { characterRepository.updateGold(any(), any()) }
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
