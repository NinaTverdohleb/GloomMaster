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
class UpdateTeamReputationUseCaseTest {
    private val teamRepository: TeamRepository = mockk()

    @Test
    fun `given reputation within range when invoked then updates`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coJustRun { teamRepository.updateReputation(5) }
        val sut = UpdateTeamReputationUseCase(teamRepository)

        // When
        sut(5)

        // Then
        coVerify(exactly = 1) { teamRepository.updateReputation(5) }
    }

    @Test
    fun `given reputation below -20 when invoked then does nothing`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val sut = UpdateTeamReputationUseCase(teamRepository)

        // When
        sut(-21)

        // Then
        coVerify(exactly = 0) { teamRepository.updateReputation(any()) }
    }

    @Test
    fun `given reputation above 20 when invoked then does nothing`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val sut = UpdateTeamReputationUseCase(teamRepository)

        // When
        sut(21)

        // Then
        coVerify(exactly = 0) { teamRepository.updateReputation(any()) }
    }
}
