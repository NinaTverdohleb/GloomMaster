package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.perks

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteCharacterPerkUseCaseTest {
    private val characterRepository: CharacterRepository = mockk()

    @Test
    fun `given character and perk ids when invoked then deleteCharacterPerk is called once`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coJustRun { characterRepository.deleteCharacterPerk(characterId = 5, perkId = 10) }
        val sut = DeleteCharacterPerkUseCase(characterRepository)

        // When
        sut(characterId = 5, perkId = 10)

        // Then
        coVerify(exactly = 1) {
            characterRepository.deleteCharacterPerk(characterId = 5, perkId = 10)
        }
    }
}
