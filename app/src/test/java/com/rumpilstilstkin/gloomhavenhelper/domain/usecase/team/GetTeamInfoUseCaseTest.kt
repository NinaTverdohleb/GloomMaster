package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@OptIn(ExperimentalCoroutinesApi::class)
class GetTeamInfoUseCaseTest {
    private val teamRepository: TeamRepository = mockk()

    @Test
    fun `given team id when invoked then returns repository result`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = ShortTeamInfo.fixture(teamId = 7)
        coEvery { teamRepository.getTeamInfo(7) } returns team
        val sut = GetTeamInfoUseCase(teamRepository)

        // When
        val result = sut(7)

        // Then
        expectThat(result).isEqualTo(team)
    }
}
