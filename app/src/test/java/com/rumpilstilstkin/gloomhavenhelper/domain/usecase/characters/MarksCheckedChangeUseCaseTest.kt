package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterShortInfo
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MarksCheckedChangeUseCaseTest {
    private val characterRepository: CharacterRepository = mockk()

    @Test
    fun `given checked true and count below cap when invoked then increments`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coEvery { characterRepository.getCharacterById(5) } returns character(checkMarkCount = 3)
        coJustRun { characterRepository.updateCheckMarks(5, 4) }
        val sut = MarksCheckedChangeUseCase(characterRepository)

        // When
        sut(characterId = 5, isChecked = true)

        // Then
        coVerify(exactly = 1) { characterRepository.updateCheckMarks(5, 4) }
    }

    @Test
    fun `given checked true and count at cap 18 when invoked then stays at 18`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coEvery { characterRepository.getCharacterById(5) } returns character(checkMarkCount = 18)
        coJustRun { characterRepository.updateCheckMarks(5, 18) }
        val sut = MarksCheckedChangeUseCase(characterRepository)

        // When
        sut(characterId = 5, isChecked = true)

        // Then
        coVerify(exactly = 1) { characterRepository.updateCheckMarks(5, 18) }
    }

    @Test
    fun `given checked false and positive count when invoked then decrements`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coEvery { characterRepository.getCharacterById(5) } returns character(checkMarkCount = 4)
        coJustRun { characterRepository.updateCheckMarks(5, 3) }
        val sut = MarksCheckedChangeUseCase(characterRepository)

        // When
        sut(characterId = 5, isChecked = false)

        // Then
        coVerify(exactly = 1) { characterRepository.updateCheckMarks(5, 3) }
    }

    @Test
    fun `given checked false and zero count when invoked then stays at zero`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coEvery { characterRepository.getCharacterById(5) } returns character(checkMarkCount = 0)
        coJustRun { characterRepository.updateCheckMarks(5, 0) }
        val sut = MarksCheckedChangeUseCase(characterRepository)

        // When
        sut(characterId = 5, isChecked = false)

        // Then
        coVerify(exactly = 1) { characterRepository.updateCheckMarks(5, 0) }
    }

    @Test
    fun `given missing character when invoked then no update`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coEvery { characterRepository.getCharacterById(999) } returns null
        val sut = MarksCheckedChangeUseCase(characterRepository)

        // When
        sut(characterId = 999, isChecked = true)

        // Then
        coVerify(exactly = 0) { characterRepository.updateCheckMarks(any(), any()) }
    }

    private fun character(checkMarkCount: Int) =
        CharacterShortInfo(
            name = "Brute",
            level = 1,
            characterType = CharacterClassType.Brute,
            isAlive = true,
            id = 5,
            teamId = 1,
            experience = 0,
            goldCount = 0,
            checkMarkCount = checkMarkCount,
            notes = "",
        )
}
