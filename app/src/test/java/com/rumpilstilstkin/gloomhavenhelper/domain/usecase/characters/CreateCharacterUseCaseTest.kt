package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterForSave
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class CreateCharacterUseCaseTest {
    private val characterRepository: CharacterRepository = mockk()
    private val teamRepository: TeamRepository = mockk()

    @Test
    fun `given current team when invoked then adds character with team id and computed exp`() = runTest(UnconfinedTestDispatcher()) {
        // Given — team has 2 retired chars; level 3 → getExpForLevel(3) = 95
        val team = ShortTeamInfo.fixture(teamId = 7).copy(countRetiredCharacters = 2)
        every { teamRepository.currentTeam } returns flowOf(team)
        val captured = slot<CharacterForSave>()
        coEvery { characterRepository.addCharacter(capture(captured)) } returns 42
        val sut = CreateCharacterUseCase(characterRepository, teamRepository)

        // When
        sut(name = "Brutus", level = 3, characterType = CharacterClassType.Brute)

        // Then
        coVerify(exactly = 1) { characterRepository.addCharacter(any()) }
        expectThat(captured.captured.name).isEqualTo("Brutus")
        expectThat(captured.captured.level).isEqualTo(3)
        expectThat(captured.captured.characterType).isEqualTo(CharacterClassType.Brute)
        expectThat(captured.captured.teamId).isEqualTo(7)
        expectThat(captured.captured.experience).isEqualTo(95)
        expectThat(captured.captured.additionalContOfPerks).isEqualTo(2)
    }

    @Test
    fun `given null current team when invoked then does nothing`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(null)
        val sut = CreateCharacterUseCase(characterRepository, teamRepository)

        // When
        sut(name = "Brutus", level = 1, characterType = CharacterClassType.Brute)

        // Then
        coVerify(exactly = 0) { characterRepository.addCharacter(any()) }
    }
}
