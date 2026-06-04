package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChangeCurrentTeamUseCaseTest {
    private val teamRepository: TeamRepository = mockk()

    @Test
    fun `given team id when invoked then forwards to setCurrentTeam`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coJustRun { teamRepository.setCurrentTeam(7) }
        val sut = ChangeCurrentTeamUseCase(teamRepository)

        // When
        sut(7)

        // Then
        coVerify(exactly = 1) { teamRepository.setCurrentTeam(7) }
    }
}
