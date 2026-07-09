package com.rumpilstilstkin.gloommaster.domain.usecase.characters

import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.RetireCharacterUseCase
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RetireCharacterUseCaseTest {
    private val characterRepository: CharacterRepository = mockk()

    @Test
    fun `given character id when invoked then leaves character`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coJustRun { characterRepository.leaveCharacter(42) }
        val sut = RetireCharacterUseCase(characterRepository)

        // When
        sut(42)

        // Then
        coVerify(exactly = 1) { characterRepository.leaveCharacter(42) }
    }
}
