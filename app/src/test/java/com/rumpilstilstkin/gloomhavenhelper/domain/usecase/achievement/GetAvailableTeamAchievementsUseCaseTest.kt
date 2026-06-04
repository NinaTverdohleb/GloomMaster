package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.achievement

import app.cash.turbine.test
import com.rumpilstilstkin.gloomhavenhelper.data.AchievementRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Achievement
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AchievementWithName
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
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

@OptIn(ExperimentalCoroutinesApi::class)
class GetAvailableTeamAchievementsUseCaseTest {
    private val teamRepository: TeamRepository = mockk()
    private val achievementRepository: AchievementRepository = mockk()

    @Test
    fun `given team with one existing achievement when invoked then returns only new achievements with names`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team =
            ShortTeamInfo.fixture(packs = listOf(PackType.MAIN, PackType.FORGOTTEN_CIRCLES))
                .copy(achievements = listOf(Achievement.fixture(slug = "alpha", isGlobal = false)))
        val all =
            listOf(
                Achievement(slug = "alpha", value = 1, maxValue = 1, isGlobal = false),
                Achievement(slug = "gamma", value = 2, maxValue = 5, isGlobal = false),
            )
        every { teamRepository.currentTeam } returns flowOf(team)
        every { achievementRepository.dictionary } returns flowOf(mapOf("gamma" to "Gamma Name"))
        coEvery { achievementRepository.getTeamAchievementsByPacks(listOf("MAIN", "FORGOTTEN_CIRCLES")) } returns all
        val sut = GetAvailableTeamAchievementsUseCase(teamRepository, achievementRepository)

        // When / Then
        sut().test {
            expectThat(awaitItem()).containsExactly(
                AchievementWithName(slug = "gamma", name = "Gamma Name", value = 2, maxValue = 5, isGlobal = false),
            )
            awaitComplete()
        }
    }
}
