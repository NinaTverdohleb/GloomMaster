package com.rumpilstilstkin.gloommaster.domain.usecase.characters

import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.ExperienceChangeUseCase
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ExperienceChangeUseCaseTest {
    private val characterRepository: CharacterRepository = mockk()

    // NOTE: ExperienceChangeUseCase is currently a pure pass-through to
    // CharacterRepository.updateExperience(id, newExp). It does NOT clamp,
    // increment, or branch — PRD bullets about negative / clamp behaviour
    // do not match the implementation. Tests reflect actual behaviour.

    @Test
    fun `given new exp when invoked then forwards to updateExperience`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coJustRun { characterRepository.updateExperience(7, 123) }
        val sut = ExperienceChangeUseCase(characterRepository)

        // When
        sut(characterId = 7, newExp = 123)

        // Then
        coVerify(exactly = 1) { characterRepository.updateExperience(7, 123) }
    }

    @Test
    fun `given negative exp when invoked then forwards verbatim`() = runTest(UnconfinedTestDispatcher()) {
        // Given — pass-through means negatives are not clamped
        coJustRun { characterRepository.updateExperience(7, -10) }
        val sut = ExperienceChangeUseCase(characterRepository)

        // When
        sut(characterId = 7, newExp = -10)

        // Then
        coVerify(exactly = 1) { characterRepository.updateExperience(7, -10) }
    }
}
