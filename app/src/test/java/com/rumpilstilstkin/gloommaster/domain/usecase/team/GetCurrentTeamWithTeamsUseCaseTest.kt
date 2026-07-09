package com.rumpilstilstkin.gloommaster.domain.usecase.team

import app.cash.turbine.test
import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.DifficultyLevel
import com.rumpilstilstkin.gloommaster.domain.entity.PackType
import com.rumpilstilstkin.gloommaster.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloommaster.domain.entity.Team
import com.rumpilstilstkin.gloommaster.domain.usecase.team.GetCurrentTeamWithTeamsUseCase
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
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

@OptIn(ExperimentalCoroutinesApi::class)
class GetCurrentTeamWithTeamsUseCaseTest {
    private val teamRepository: TeamRepository = mockk()

    @Test
    fun `given current team and team list when invoked then emits current and other teams`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = ShortTeamInfo.fixture(teamId = 7, teamName = "Current")
        val asTeam =
            Team(teamId = 7, name = "Current", packs = listOf(PackType.MAIN), difficultyLevel = DifficultyLevel.NORMAL)
        val other =
            Team(teamId = 8, name = "Other", packs = listOf(PackType.MAIN), difficultyLevel = DifficultyLevel.NORMAL)
        every { teamRepository.currentTeam } returns flowOf(team)
        every { teamRepository.getTeams() } returns flowOf(listOf(asTeam, other))
        val sut = GetCurrentTeamWithTeamsUseCase(teamRepository)

        // When / Then
        sut().test {
            val (current, others) = awaitItem()
            expectThat(current).isEqualTo(asTeam)
            expectThat(others).containsExactly(other)
            awaitComplete()
        }
    }

    @Test
    fun `given null current team when invoked then emits null and empty list`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(null)
        every { teamRepository.getTeams() } returns flowOf(emptyList())
        val sut = GetCurrentTeamWithTeamsUseCase(teamRepository)

        // When / Then
        sut().test {
            val (current, others) = awaitItem()
            expectThat(current).isNull()
            expectThat(others).isEmpty()
            awaitComplete()
        }
    }
}
