package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters

import app.cash.turbine.test
import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterInfo
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class GetCharacterGeneralInfoFlowUseCaseTest {
    private val characterRepository: CharacterRepository = mockk()

    @Test
    fun `given character id when invoked then returns repository flow`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val info =
            CharacterInfo(
                name = "Brute",
                level = 1,
                characterType = CharacterClassType.Brute,
                isAlive = true,
                id = 5,
                team = null,
                experience = 0,
                goldCount = 0,
                checkMarkCount = 0,
                notes = "",
                additionalContOfPerks = 0,
            )
        every { characterRepository.getCharacterByIdFlow(5) } returns flowOf(info)
        val sut = GetCharacterGeneralInfoFlowUseCase(characterRepository)

        // When / Then
        sut(5).test {
            expectThat(awaitItem()).isEqualTo(info)
            awaitComplete()
        }
    }
}
