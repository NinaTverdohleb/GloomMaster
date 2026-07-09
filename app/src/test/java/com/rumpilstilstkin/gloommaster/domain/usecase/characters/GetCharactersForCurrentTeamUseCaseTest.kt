package com.rumpilstilstkin.gloommaster.domain.usecase.characters

import app.cash.turbine.test
import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterInfo
import com.rumpilstilstkin.gloommaster.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.GetCharactersForCurrentTeamUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEmpty

@OptIn(ExperimentalCoroutinesApi::class)
class GetCharactersForCurrentTeamUseCaseTest {
    private val characterRepository: CharacterRepository = mockk()
    private val teamRepository: TeamRepository = mockk()

    @Test
    fun `given current team when invoked then forwards to character repo`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = ShortTeamInfo.fixture(teamId = 7)
        val list = listOf(character(1), character(2))
        every { teamRepository.currentTeam } returns flowOf(team)
        every { characterRepository.getCharacterByTeamId(7) } returns flowOf(list)
        val sut = GetCharactersForCurrentTeamUseCase(characterRepository, teamRepository)

        // When / Then
        sut().test {
            expectThat(awaitItem()).containsExactly(list)
            awaitComplete()
        }
    }

    @Test
    fun `given null current team when invoked then emits empty list`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(null)
        val sut = GetCharactersForCurrentTeamUseCase(characterRepository, teamRepository)

        // When / Then
        sut().test {
            expectThat(awaitItem()).isEmpty()
            awaitComplete()
        }
    }

    private fun character(id: Int) =
        CharacterInfo(
            name = "Brute $id",
            level = 1,
            characterType = CharacterClassType.Brute,
            isAlive = true,
            id = id,
            team = null,
            experience = 0,
            goldCount = 0,
            checkMarkCount = 0,
            notes = "",
            additionalContOfPerks = 0,
        )
}
