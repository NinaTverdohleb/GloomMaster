package com.rumpilstilstkin.gloommaster.domain.usecase.achievement

import app.cash.turbine.test
import com.rumpilstilstkin.gloommaster.data.AchievementRepository
import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.Achievement
import com.rumpilstilstkin.gloommaster.domain.entity.AchievementWithName
import com.rumpilstilstkin.gloommaster.domain.entity.PackType
import com.rumpilstilstkin.gloommaster.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloommaster.domain.usecase.achievement.GetAvailableGlobalAchievementsUseCase
import io.mockk.coEvery
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
class GetAvailableGlobalAchievementsUseCaseTest {
    private val teamRepository: TeamRepository = mockk()
    private val achievementRepository: AchievementRepository = mockk()

    @Test
    fun `given team with one existing achievement when invoked then returns only new achievements with names`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team =
            ShortTeamInfo.fixture(packs = listOf(PackType.MAIN))
                .copy(achievements = listOf(Achievement.fixture(slug = "alpha", isGlobal = true)))
        val all =
            listOf(
                Achievement(slug = "alpha", value = 1, maxValue = 1, isGlobal = true),
                Achievement(slug = "beta", value = 0, maxValue = 2, isGlobal = true),
            )
        every { teamRepository.currentTeam } returns flowOf(team)
        every { achievementRepository.dictionary } returns flowOf(mapOf("beta" to "Beta Name"))
        coEvery { achievementRepository.getGlobalAchievementsByPacks(listOf("MAIN")) } returns all
        val sut = GetAvailableGlobalAchievementsUseCase(teamRepository, achievementRepository)

        // When / Then
        sut().test {
            expectThat(awaitItem()).containsExactly(
                AchievementWithName(slug = "beta", name = "Beta Name", value = 0, maxValue = 2, isGlobal = true),
            )
            awaitComplete()
        }
    }

    @Test
    fun `given null current team when invoked then returns empty list`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(null)
        every { achievementRepository.dictionary } returns flowOf(emptyMap())
        val sut = GetAvailableGlobalAchievementsUseCase(teamRepository, achievementRepository)

        // When / Then
        sut().test {
            expectThat(awaitItem()).isEmpty()
            awaitComplete()
        }
    }
}
