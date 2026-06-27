package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.data.MonsterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AvailableCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly

@OptIn(ExperimentalCoroutinesApi::class)
class AddMonstersForCurrentScenarioUseCaseTest {
    private val scenarioGameStateRepository: ScenarioGameStateRepository = mockk()
    private val monsterRepository: MonsterRepository = mockk()
    private val localeRepository: LocaleRepository = mockk()

    @Test
    fun `given active scenario when invoked then monster slugs and cards are merged`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val state =
            ScenarioGameState(
                level = 1,
                scenarioNumber = 1,
                monsterSlugs = listOf("old-mon"),
                round = 0,
                availableCards = listOf(AvailableCard("oldDeck", 1)),
                activeMonsters = emptyList(),
                magicCharges = emptyList(),
            )
        val newMonster =
            Monster(
                slug = "bandit",
                name = "Bandit",
                life = 5,
                stats = emptyList(),
                eliteLife = 7,
                eliteStats = emptyList(),
                deckName = "banditDeck",
                cards =
                    listOf(
                        MonsterCard(deckName = "banditDeck", cardId = 10, actions = emptyList(), initiative = 1),
                        MonsterCard(deckName = "banditDeck", cardId = 11, actions = emptyList(), initiative = 2),
                    ),
                isBoss = false,
                immunity = emptySet(),
                isFly = false,
                level = 0,
                lifeMultiple = false,
            )
        coEvery { scenarioGameStateRepository.get() } returns state
        coEvery { localeRepository.getCurrentLocale() } returns "en"
        coEvery { monsterRepository.getMonstersBySlugs(listOf("bandit"), 0, "en") } returns listOf(newMonster)
        val captured = slot<ScenarioGameState>()
        coJustRun { scenarioGameStateRepository.save(capture(captured)) }
        val sut = AddMonstersForCurrentScenarioUseCase(scenarioGameStateRepository, monsterRepository, localeRepository)

        // When
        sut(listOf("bandit"))

        // Then
        expectThat(captured.captured.monsterSlugs).containsExactly("old-mon", "bandit")
        expectThat(captured.captured.availableCards).containsExactly(
            AvailableCard("oldDeck", 1),
            AvailableCard("banditDeck", 10),
            AvailableCard("banditDeck", 11),
        )
    }

    @Test
    fun `given no active scenario when invoked then nothing happens`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        coEvery { scenarioGameStateRepository.get() } returns null
        val sut = AddMonstersForCurrentScenarioUseCase(scenarioGameStateRepository, monsterRepository, localeRepository)

        // When
        sut(listOf("bandit"))

        // Then
        coVerify(exactly = 0) { scenarioGameStateRepository.save(any()) }
    }
}
