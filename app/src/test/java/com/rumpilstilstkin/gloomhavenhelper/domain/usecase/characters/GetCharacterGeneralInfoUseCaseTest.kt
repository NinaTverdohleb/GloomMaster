package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterShortInfo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class GetCharacterGeneralInfoUseCaseTest {
    private val characterRepository: CharacterRepository = mockk()

    @Test
    fun `given character id when invoked then returns repository result`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val info =
            CharacterShortInfo(
                name = "Brute",
                level = 1,
                characterType = CharacterClassType.Brute,
                isAlive = true,
                id = 5,
                teamId = 1,
                experience = 0,
                goldCount = 0,
                checkMarkCount = 0,
                notes = "",
            )
        coEvery { characterRepository.getCharacterById(5) } returns info
        val sut = GetCharacterGeneralInfoUseCase(characterRepository)

        // When
        val result = sut(5)

        // Then
        expectThat(result).isEqualTo(info)
    }
}
