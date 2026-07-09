package com.rumpilstilstkin.gloommaster.domain.usecase.scenario

import com.rumpilstilstkin.gloommaster.data.AchievementRepository
import com.rumpilstilstkin.gloommaster.data.LocaleRepository
import com.rumpilstilstkin.gloommaster.data.ScenarioRepository
import com.rumpilstilstkin.gloommaster.domain.entity.Achievement
import com.rumpilstilstkin.gloommaster.domain.entity.LogicalCondition
import com.rumpilstilstkin.gloommaster.domain.entity.PackType
import com.rumpilstilstkin.gloommaster.domain.entity.ScenarioInfoWithName
import com.rumpilstilstkin.gloommaster.domain.entity.ScenarioShortInfo
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.FilterTeamScenariosUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.isEmpty

@OptIn(ExperimentalCoroutinesApi::class)
class FilterTeamScenariosUseCaseTest {
    private val localeRepository: LocaleRepository = mockk()
    private val scenarioRepository: ScenarioRepository = mockk()
    private val achievementRepository: AchievementRepository = mockk()

    @Test
    fun `given empty achievements when invoked then only no-requirement scenarios are active`() = runTest(UnconfinedTestDispatcher()) {
        // Given — scenario 1 has no requirement; 2 requires achievement
        coEvery { localeRepository.getCurrentLocale() } returns "en"
        coEvery { achievementRepository.currentDictionary() } returns emptyMap()
        coEvery { scenarioRepository.getScenariosWithName(any(), "en") } returns
            listOf(
                scenario(1, requirements = ""),
                scenario(2, requirements = "Achievement A"),
            )
        val sut =
            FilterTeamScenariosUseCase(localeRepository, scenarioRepository, achievementRepository)

        // When
        val result = sut(emptyList(), emptyList())

        // Then
        expectThat(result.activeScenarios.map { it.scenarioNumber }).containsExactly(1)
        expectThat(result.blockedScenarios.map { it.scenarioNumber }).containsExactly(2)
        expectThat(result.completedScenarios).isEmpty()
    }

    @Test
    fun `given matching achievement when invoked then requirement-gated scenario is active`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coEvery { localeRepository.getCurrentLocale() } returns "en"
        coEvery { achievementRepository.currentDictionary() } returns
            mapOf("ach.a" to "Achievement A")
        coEvery { scenarioRepository.getScenariosWithName(any(), "en") } returns
            listOf(scenario(2, requirements = "Achievement A"))
        val achievements =
            listOf(Achievement(slug = "ach.a", value = 1, maxValue = 1, isGlobal = false))
        val sut =
            FilterTeamScenariosUseCase(localeRepository, scenarioRepository, achievementRepository)

        // When
        val result = sut(achievements, emptyList())

        // Then
        expectThat(result.activeScenarios.map { it.scenarioNumber }).containsExactly(2)
        expectThat(result.blockedScenarios).isEmpty()
    }

    @Test
    fun `given mismatched dictionary entry when invoked then requirement is not satisfied`() = runTest(UnconfinedTestDispatcher()) {
        // Given — dictionary has translation for ach.b only, scenario asks for "Achievement A"
        coEvery { localeRepository.getCurrentLocale() } returns "en"
        coEvery { achievementRepository.currentDictionary() } returns
            mapOf("ach.b" to "Achievement B")
        coEvery { scenarioRepository.getScenariosWithName(any(), "en") } returns
            listOf(scenario(2, requirements = "Achievement A"))
        val achievements =
            listOf(Achievement(slug = "ach.b", value = 1, maxValue = 1, isGlobal = false))
        val sut =
            FilterTeamScenariosUseCase(localeRepository, scenarioRepository, achievementRepository)

        // When
        val result = sut(achievements, emptyList())

        // Then
        expectThat(result.activeScenarios).isEmpty()
        expectThat(result.blockedScenarios.map { it.scenarioNumber }).containsExactly(2)
    }

    @Test
    fun `given completed scenarios when invoked then they go to completed bucket`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coEvery { localeRepository.getCurrentLocale() } returns "en"
        coEvery { achievementRepository.currentDictionary() } returns emptyMap()
        coEvery { scenarioRepository.getScenariosWithName(any(), "en") } returns
            listOf(scenario(1, requirements = "", isCompleted = true))
        val sut =
            FilterTeamScenariosUseCase(localeRepository, scenarioRepository, achievementRepository)

        // When
        val result = sut(emptyList(), listOf(ScenarioShortInfo.fixture(scenarioNumber = 1, isCompleted = true)))

        // Then
        expectThat(result.completedScenarios.map { it.scenarioNumber }).containsExactly(1)
        expectThat(result.activeScenarios).isEmpty()
        expectThat(result.blockedScenarios).isEmpty()
    }

    private fun scenario(
        num: Int,
        requirements: String,
        isCompleted: Boolean = false,
    ) = ScenarioInfoWithName(
        scenarioNumber = num,
        scenarioName = "Scenario $num",
        scenarioRequirements = LogicalCondition(requirements),
        newScenario = emptyList(),
        location = "Loc",
        pack = PackType.MAIN,
        monsters = emptyList(),
        isCompleted = isCompleted,
    )
}
