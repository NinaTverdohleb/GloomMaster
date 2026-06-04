package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import app.cash.turbine.test
import com.rumpilstilstkin.gloomhavenhelper.data.AchievementRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Achievement
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

@OptIn(ExperimentalCoroutinesApi::class)
class GetCurrentTeamShortInfoUseCaseTest {
    private val teamRepository: TeamRepository = mockk()
    private val achievementRepository: AchievementRepository = mockk()

    @Test
    fun `given team and dictionary when invoked then emits team with translated achievements`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team =
            ShortTeamInfo.fixture(teamId = 7).copy(
                achievements =
                    listOf(
                        Achievement(slug = "slug1", value = 1, maxValue = 1, isGlobal = false),
                        Achievement(slug = "slug2", value = 2, maxValue = 3, isGlobal = true),
                    ),
            )
        val dictionary = mapOf("slug1" to "Translated 1", "slug2" to "Translated 2")
        every { teamRepository.currentTeam } returns flowOf(team)
        every { achievementRepository.dictionary } returns flowOf(dictionary)
        val sut = GetCurrentTeamShortInfoUseCase(teamRepository, achievementRepository)

        // When / Then
        sut().test {
            val item = awaitItem()!!
            expectThat(item.teamId).isEqualTo(7)
            expectThat(item.achievements).hasSize(2)
            expectThat(item.achievements[0].name).isEqualTo("Translated 1")
            expectThat(item.achievements[1].name).isEqualTo("Translated 2")
            awaitComplete()
        }
    }

    @Test
    fun `given null current team when invoked then emits null`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { teamRepository.currentTeam } returns flowOf(null)
        every { achievementRepository.dictionary } returns flowOf(emptyMap())
        val sut = GetCurrentTeamShortInfoUseCase(teamRepository, achievementRepository)

        // When / Then
        sut().test {
            expectThat(awaitItem()).isNull()
            awaitComplete()
        }
    }
}
