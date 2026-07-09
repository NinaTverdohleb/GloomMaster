package com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play

import com.rumpilstilstkin.gloommaster.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloommaster.domain.entity.ScenarioGameState
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play.SaveScenarioStateUseCase
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SaveScenarioStateUseCaseTest {
    private val scenarioGameStateRepository: ScenarioGameStateRepository = mockk()

    @Test
    fun `given state when invoked then forwarded to repository`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val state =
            ScenarioGameState(
                level = 3,
                scenarioNumber = 42,
                monsterSlugs = emptyList(),
                round = 0,
                availableCards = emptyList(),
                activeMonsters = emptyList(),
                magicCharges = emptyList(),
            )
        coJustRun { scenarioGameStateRepository.save(state) }
        val sut = SaveScenarioStateUseCase(scenarioGameStateRepository)

        // When
        sut(state)

        // Then
        coVerify(exactly = 1) { scenarioGameStateRepository.save(state) }
    }
}
