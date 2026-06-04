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
class UpdateCharacterLevelUseCaseTest {
    private val characterRepository: CharacterRepository = mockk()

    @Test
    fun `given level when invoked then sets level with matching exp`() = runTest(UnconfinedTestDispatcher()) {
        // Given — getExpForLevel(4) = 150
        coJustRun { characterRepository.setLevel(id = 5, level = 4, experience = 150) }
        val sut = UpdateCharacterLevelUseCase(characterRepository)

        // When
        sut(characterId = 5, level = 4)

        // Then
        coVerify(exactly = 1) { characterRepository.setLevel(id = 5, level = 4, experience = 150) }
    }
}
