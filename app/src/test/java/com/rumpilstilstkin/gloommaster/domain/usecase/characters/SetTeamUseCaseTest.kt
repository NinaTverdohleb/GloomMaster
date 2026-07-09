package com.rumpilstilstkin.gloommaster.domain.usecase.characters

import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.SetTeamUseCase
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SetTeamUseCaseTest {
    private val characterRepository: CharacterRepository = mockk()

    @Test
    fun `given character id and team id when invoked then sets team`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coJustRun { characterRepository.setTeam(5, 7) }
        val sut = SetTeamUseCase(characterRepository)

        // When
        sut(characterId = 5, teamId = 7)

        // Then
        coVerify(exactly = 1) { characterRepository.setTeam(5, 7) }
    }
}
