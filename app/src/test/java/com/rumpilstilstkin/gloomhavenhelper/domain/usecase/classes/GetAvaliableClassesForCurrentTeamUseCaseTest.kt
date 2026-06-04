package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.classes

import app.cash.turbine.test
import com.rumpilstilstkin.gloomhavenhelper.data.CharacterClassRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
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

    @Test
    fun `given null current team when invoked then returns empty list`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(null)
        val sut = GetAvaliableClassesForCurrentTeamUseCase(teamRepository, characterClassRepository)

        // When / Then
        sut().test {
            expectThat(awaitItem()).isEmpty()
            awaitComplete()
        }
    }
}
