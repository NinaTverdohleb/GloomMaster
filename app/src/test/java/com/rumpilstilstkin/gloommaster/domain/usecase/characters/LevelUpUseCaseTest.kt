package com.rumpilstilstkin.gloommaster.domain.usecase.characters

import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterShortInfo
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.LevelUpUseCase
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LevelUpUseCaseTest {
    private val characterRepository: CharacterRepository = mockk()

    // NOTE: LevelUpUseCase currently increments character.level + 1 unconditionally,
    // with no XP threshold check and no cap at 8. PRD bullets about
    // "XP exceeds next level / cap at 8 / insufficient XP" do not match the
    // implementation. Tests reflect actual behaviour.

    @Test
    fun `given existing character when invoked then level is incremented by 1`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coEvery { characterRepository.getCharacterById(5) } returns character(level = 3)
        coJustRun { characterRepository.updateLevel(5, 4) }
        val sut = LevelUpUseCase(characterRepository)

        // When
        sut(5)

        // Then
        coVerify(exactly = 1) { characterRepository.updateLevel(5, 4) }
    }

    @Test
    fun `given character at level 8 when invoked then level becomes 9 (no cap)`() = runTest(UnconfinedTestDispatcher()) {
        // Given — current implementation does NOT cap
        coEvery { characterRepository.getCharacterById(5) } returns character(level = 8)
        coJustRun { characterRepository.updateLevel(5, 9) }
        val sut = LevelUpUseCase(characterRepository)

        // When
        sut(5)

        // Then
        coVerify(exactly = 1) { characterRepository.updateLevel(5, 9) }
    }

    @Test
    fun `given missing character when invoked then no update`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coEvery { characterRepository.getCharacterById(999) } returns null
        val sut = LevelUpUseCase(characterRepository)

        // When
        sut(999)

        // Then
        coVerify(exactly = 0) { characterRepository.updateLevel(any(), any()) }
    }

    private fun character(level: Int) =
        CharacterShortInfo(
            name = "Brute",
            level = level,
            characterType = CharacterClassType.Brute,
            isAlive = true,
            id = 5,
            teamId = 1,
            experience = 0,
            goldCount = 0,
            checkMarkCount = 0,
            notes = "",
        )
}
