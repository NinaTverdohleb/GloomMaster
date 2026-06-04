package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateCharacterNameUseCaseTest {
    private val characterRepository: CharacterRepository = mockk()

    @Test
    fun `given new name when invoked then forwards to updateName`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coJustRun { characterRepository.updateName(5, "Brutus") }
        val sut = UpdateCharacterNameUseCase(characterRepository)

        // When
        sut(characterId = 5, name = "Brutus")

        // Then
        coVerify(exactly = 1) { characterRepository.updateName(5, "Brutus") }
    }
}
