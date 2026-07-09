package com.rumpilstilstkin.gloommaster.domain.usecase.achievement

import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.Achievement
import com.rumpilstilstkin.gloommaster.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloommaster.domain.usecase.achievement.AddOrUpdateAchievementUseCase
import io.mockk.coJustRun
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
import strikt.assertions.containsExactlyInAnyOrder
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateAchievementUseCaseTest {
    private val teamRepository: TeamRepository = mockk()

    @Test
    fun `given existing achievement when invoked then updateTeam replaces it with the new value`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team =
            ShortTeamInfo.fixture().copy(
                achievements =
                    listOf(
                        Achievement.fixture(slug = "alpha", value = 1, maxValue = 5),
                        Achievement.fixture(slug = "beta", value = 0, maxValue = 1),
                    ),
            )
        val updated = Achievement.fixture(slug = "alpha", value = 4, maxValue = 5)
        val capturedTeam = slot<ShortTeamInfo>()
        every { teamRepository.currentTeam } returns flowOf(team)
        coJustRun { teamRepository.updateTeam(capture(capturedTeam)) }
        val sut = AddOrUpdateAchievementUseCase(teamRepository)

        // When
        sut(updated)

        // Then
        coVerify(exactly = 1) { teamRepository.updateTeam(any()) }
        val resultAchievements = capturedTeam.captured.achievements
        expectThat(resultAchievements.map { it.slug }).containsExactlyInAnyOrder("alpha", "beta")
        expectThat(resultAchievements.first { it.slug == "alpha" }.value).isEqualTo(4)
    }
}
