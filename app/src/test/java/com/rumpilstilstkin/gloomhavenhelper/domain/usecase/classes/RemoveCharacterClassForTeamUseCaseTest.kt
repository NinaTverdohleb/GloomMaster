package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.classes

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterClassRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
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
class RemoveCharacterClassForTeamUseCaseTest {
    private val teamRepository: TeamRepository = mockk()
    private val characterClassRepository: CharacterClassRepository = mockk()

    @Test
    fun `given current team when invoked then removeAvailableClass is called with team id and type`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = ShortTeamInfo.fixture(teamId = 9)
        every { teamRepository.currentTeam } returns flowOf(team)
        coJustRun {
            characterClassRepository.removeAvailableClass(teamId = 9, type = CharacterClassType.Spellweaver)
        }
        val sut = RemoveCharacterClassForTeamUseCase(teamRepository, characterClassRepository)

        // When
        sut(CharacterClassType.Spellweaver)

        // Then
        coVerify(exactly = 1) {
            characterClassRepository.removeAvailableClass(teamId = 9, type = CharacterClassType.Spellweaver)
        }
    }
}
