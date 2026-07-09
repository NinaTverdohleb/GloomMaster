package com.rumpilstilstkin.gloommaster.domain.usecase.classes

import app.cash.turbine.test
import com.rumpilstilstkin.gloommaster.data.CharacterClassRepository
import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloommaster.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloommaster.domain.usecase.classes.GetAvaliableClassesForCurrentTeamUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly

@OptIn(ExperimentalCoroutinesApi::class)
class GetAvaliableClassesForCurrentTeamUseCaseTest {
    private val teamRepository: TeamRepository = mockk()
    private val characterClassRepository: CharacterClassRepository = mockk()

    @Test
    fun `given current team when invoked then forwards repo flow for that team`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = ShortTeamInfo.fixture(teamId = 3)
        val classes = listOf(CharacterClassType.Brute, CharacterClassType.Spellweaver)
        every { teamRepository.currentTeam } returns flowOf(team)
        every { characterClassRepository.getAvailableClassesForTeam(3) } returns flowOf(classes)
        val sut = GetAvaliableClassesForCurrentTeamUseCase(teamRepository, characterClassRepository)

        // When / Then
        sut().test {
            expectThat(awaitItem()).containsExactly(CharacterClassType.Brute, CharacterClassType.Spellweaver)
            awaitComplete()
        }
    }
}
