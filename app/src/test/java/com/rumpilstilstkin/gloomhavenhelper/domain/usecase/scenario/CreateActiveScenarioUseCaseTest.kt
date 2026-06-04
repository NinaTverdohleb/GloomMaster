package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.data.MonsterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AvailableCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.DifficultyLevel
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Magic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Prosperity
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamUseCase
import io.mockk.coEvery
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
import strikt.assertions.containsExactly
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CreateActiveScenarioUseCaseTest {
    private val getCurrentTeamUseCase: GetCurrentTeamUseCase = mockk()
    private val monsterRepository: MonsterRepository = mockk()
    private val scenarioGameStateRepository: ScenarioGameStateRepository = mockk()
    private val clearCurrentActiveScenarioUseCase: ClearCurrentActiveScenarioUseCase = mockk(relaxed = true)

    @Test
    fun `given current team and scenario number when invoked then state is saved with team level and monsters`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { getCurrentTeamUseCase() } returns flowOf(teamInfo(level = 4))
        coEvery { monsterRepository.getMonsterSlugsForScenario(42) } returns listOf("bandit", "ooze")
        val cards = listOf(AvailableCard("banditDeck", 1), AvailableCard("oozeDeck", 2))
        coEvery { monsterRepository.getMonsterCards(listOf("bandit", "ooze")) } returns cards
        val captured = slot<ScenarioGameState>()
        coJustRun { scenarioGameStateRepository.save(capture(captured)) }
        val sut =
            CreateActiveScenarioUseCase(
                getCurrentTeamUseCase,
                monsterRepository,
                scenarioGameStateRepository,
                clearCurrentActiveScenarioUseCase,
            )

        // When
        val result = sut(42)

        // Then
        expectThat(result.isSuccess).isTrue()
        coVerify(exactly = 1) { clearCurrentActiveScenarioUseCase() }
        val saved = captured.captured
        expectThat(saved.scenarioNumber).isEqualTo(42)
        expectThat(saved.level).isEqualTo(4)
        expectThat(saved.monsterSlugs).containsExactly("bandit", "ooze")
        expectThat(saved.availableCards).isEqualTo(cards)
        expectThat(saved.round).isEqualTo(0)
        expectThat(saved.activeMonsters).isEmpty()
        expectThat(saved.magicCharges.map { it.name }).containsExactly(
            Magic.FIRE.name,
            Magic.FROST.name,
            Magic.AIR.name,
            Magic.EARTH.name,
            Magic.SUN.name,
            Magic.MOON.name,
        )
        expectThat(saved.magicCharges.all { it.value == 0 }).isTrue()
    }

    @Test
    fun `given null scenario number when invoked then state has empty monsters`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { getCurrentTeamUseCase() } returns flowOf(teamInfo(level = 1))
        coEvery { monsterRepository.getMonsterCards(emptyList()) } returns emptyList()
        val captured = slot<ScenarioGameState>()
        coJustRun { scenarioGameStateRepository.save(capture(captured)) }
        val sut =
            CreateActiveScenarioUseCase(
                getCurrentTeamUseCase,
                monsterRepository,
                scenarioGameStateRepository,
                clearCurrentActiveScenarioUseCase,
            )

        // When
        val result = sut(null)

        // Then
        expectThat(result.isSuccess).isTrue()
        expectThat(captured.captured.scenarioNumber).isEqualTo(null)
        expectThat(captured.captured.monsterSlugs).isEmpty()
    }

    @Test
    fun `given null current team when invoked then returns failure`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        every { getCurrentTeamUseCase() } returns flowOf(null)
        val sut =
            CreateActiveScenarioUseCase(
                getCurrentTeamUseCase,
                monsterRepository,
                scenarioGameStateRepository,
                clearCurrentActiveScenarioUseCase,
            )

        // When
        val result = sut(42)

        // Then
        expectThat(result.isFailure).isTrue()
        expectThat(result.isSuccess).isFalse()
        coVerify(exactly = 0) { scenarioGameStateRepository.save(any()) }
    }

    private fun teamInfo(level: Int) =
        TeamInfo(
            id = 7,
            name = "Team",
            level = level,
            teamAchievement = emptyList(),
            globalAchievement = emptyList(),
            reputation = 0,
            activeScenario = emptyList(),
            aliveCharacters = emptyList(),
            shopDiscount = 0,
            prosperity = Prosperity.fixture(),
            packs = listOf(PackType.MAIN),
            hasActiveScenario = false,
            churchValue = 0,
            difficultyLevel = DifficultyLevel.NORMAL,
        )
}
