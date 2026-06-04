package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.achievement

import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Achievement
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import io.mockk.coVerify
import io.mockk.coJustRun
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly

@OptIn(ExperimentalCoroutinesApi::class)
class RemoveAchievementUseCaseTest {
    private val teamRepository: TeamRepository = mockk()

    @Test
    fun `given current team with achievement when invoked then updateTeam called without the removed slug`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team =
            ShortTeamInfo.fixture().copy(
                achievements =
                    listOf(
                        Achievement.fixture(slug = "alpha"),
                        Achievement.fixture(slug = "beta"),
                    ),
            )
        val capturedTeam = slot<ShortTeamInfo>()
        every { teamRepository.currentTeam } returns flowOf(team)
        coJustRun { teamRepository.updateTeam(capture(capturedTeam)) }
        val sut = RemoveAchievementUseCase(teamRepository)

        // When
        sut("alpha")

        // Then
        coVerify(exactly = 1) { teamRepository.updateTeam(any()) }
        expectThat(capturedTeam.captured.achievements.map { it.slug }).containsExactly("beta")
    }
}
