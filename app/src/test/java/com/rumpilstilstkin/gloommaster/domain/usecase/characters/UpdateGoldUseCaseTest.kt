package com.rumpilstilstkin.gloommaster.domain.usecase.characters

import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.UpdateGoldUseCase
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateGoldUseCaseTest {
    private val characterRepository: CharacterRepository = mockk()

    @Test
    fun `given new gold when invoked then forwards to updateGold`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coJustRun { characterRepository.updateGold(5, 99) }
        val sut = UpdateGoldUseCase(characterRepository)

        // When
        sut(characterId = 5, newGoldCount = 99)

        // Then
        coVerify(exactly = 1) { characterRepository.updateGold(5, 99) }
    }
}
