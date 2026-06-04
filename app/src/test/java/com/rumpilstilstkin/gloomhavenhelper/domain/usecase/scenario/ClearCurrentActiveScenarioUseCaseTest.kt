package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioGameStateRepository
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ClearCurrentActiveScenarioUseCaseTest {
    private val scenarioGameStateRepository: ScenarioGameStateRepository = mockk()

    @Test
    fun `given any state when invoked then delete is called`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coJustRun { scenarioGameStateRepository.delete() }
        val sut = ClearCurrentActiveScenarioUseCase(scenarioGameStateRepository)

        // When
        sut()

        // Then
        coVerify(exactly = 1) { scenarioGameStateRepository.delete() }
    }
}
