package com.rumpilstilstkin.gloommaster.domain.usecase.classes

import com.rumpilstilstkin.gloommaster.data.CharacterClassRepository
import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloommaster.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloommaster.domain.usecase.classes.AddCharacterClassForTeamUseCase
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddCharacterClassForTeamUseCaseTest {
    private val teamRepository: TeamRepository = mockk()
    private val characterClassRepository: CharacterClassRepository = mockk()

    @Test
    fun `given current team when invoked then addAvailableClass is called with team id and type`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = ShortTeamInfo.fixture(teamId = 7)
        every { teamRepository.currentTeam } returns flowOf(team)
        coJustRun {
            characterClassRepository.addAvailableClass(teamId = 7, type = CharacterClassType.Brute)
        }
        val sut = AddCharacterClassForTeamUseCase(teamRepository, characterClassRepository)

        // When
        sut(CharacterClassType.Brute)

        // Then
        coVerify(exactly = 1) {
            characterClassRepository.addAvailableClass(teamId = 7, type = CharacterClassType.Brute)
        }
    }
}
