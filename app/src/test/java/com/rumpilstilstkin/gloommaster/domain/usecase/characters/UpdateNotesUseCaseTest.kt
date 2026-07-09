package com.rumpilstilstkin.gloommaster.domain.usecase.characters

import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.UpdateNotesUseCase
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateNotesUseCaseTest {
    private val characterRepository: CharacterRepository = mockk()

    @Test
    fun `given new notes when invoked then forwards to updateNotes`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coJustRun { characterRepository.updateNotes(5, "boss tomorrow") }
        val sut = UpdateNotesUseCase(characterRepository)

        // When
        sut(characterId = 5, newNotes = "boss tomorrow")

        // Then
        coVerify(exactly = 1) { characterRepository.updateNotes(5, "boss tomorrow") }
    }
}
