package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.perks

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddPerksForCharacterUseCaseTest {
    private val characterRepository: CharacterRepository = mockk()

    @Test
    fun `given list of perk ids when invoked then addCharacterPerk is called once per id in order`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coJustRun { characterRepository.addCharacterPerk(characterId = any(), perkId = any()) }
        val sut = AddPerksForCharacterUseCase(characterRepository)

        // When
        sut(perksIds = listOf(10, 20, 30), characterId = 5)

        // Then
        coVerifyOrder {
            characterRepository.addCharacterPerk(characterId = 5, perkId = 10)
            characterRepository.addCharacterPerk(characterId = 5, perkId = 20)
            characterRepository.addCharacterPerk(characterId = 5, perkId = 30)
        }
    }

    @Test
    fun `given empty perk list when invoked then addCharacterPerk is never called`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val sut = AddPerksForCharacterUseCase(characterRepository)

        // When
        sut(perksIds = emptyList(), characterId = 5)

        // Then
        coVerify(exactly = 0) {
            characterRepository.addCharacterPerk(characterId = any(), perkId = any())
        }
    }
}
