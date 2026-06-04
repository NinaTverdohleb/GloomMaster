package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import app.cash.turbine.test
import com.rumpilstilstkin.gloomhavenhelper.data.MonsterRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.DifficultyLevel
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Prosperity
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetMonsterForScenarioUseCaseTest {
    private val monsterRepository: MonsterRepository = mockk()
    private val getCurrentTeamUseCase: GetCurrentTeamUseCase = mockk()

    @Test
    fun `given current team when invoked then returns success with monster slugs`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { getCurrentTeamUseCase() } returns flowOf(teamInfo())
        coEvery { monsterRepository.getMonsterSlugsForScenario(42) } returns listOf("bandit", "ooze")
        val sut = GetMonsterForScenarioUseCase(monsterRepository, getCurrentTeamUseCase)

        // When
        val result = sut(42)

        // Then
        expectThat(result.isSuccess).isTrue()
        expectThat(result.getOrThrow()).isEqualTo(listOf("bandit", "ooze"))
    }

    @Test
    fun `given null current team when invoked then returns failure`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { getCurrentTeamUseCase() } returns flowOf(null)
        val sut = GetMonsterForScenarioUseCase(monsterRepository, getCurrentTeamUseCase)

        // When
        val result = sut(42)

        // Then
        expectThat(result.isFailure).isTrue()
        expectThat(result.isSuccess).isFalse()
    }

    private fun teamInfo() =
        TeamInfo(
            id = 7,
            name = "Team",
            level = 1,
            teamAchievement = emptyList(),
            globalAchievement = emptyList(),
            reputation = 0,
            activeScenario = emptyList(),
            aliveCharacters = emptyList(),
            shopDiscount = 0,
            prosperity = Prosperity.fixture(),
            packs = listOf(PackType.MAIN),
            hasActiveScenario = false,
            churchValue = 0,
            difficultyLevel = DifficultyLevel.NORMAL,
        )
}
