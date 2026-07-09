package com.rumpilstilstkin.gloommaster.domain.usecase.characters

import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.DeleteCharacterUseCase
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteCharacterUseCaseTest {
    private val characterRepository: CharacterRepository = mockk()

    @Test
    fun `given character id when invoked then deletes character`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coJustRun { characterRepository.deleteCharacter(42) }
        val sut = DeleteCharacterUseCase(characterRepository)

        // When
        sut(42)

        // Then
        coVerify(exactly = 1) { characterRepository.deleteCharacter(42) }
    }
}
