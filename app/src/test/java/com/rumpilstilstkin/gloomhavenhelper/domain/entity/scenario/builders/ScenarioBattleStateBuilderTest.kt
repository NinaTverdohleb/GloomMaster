package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.builders

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AvailableCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.DifficultyLevel
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LevelInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Magic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Prosperity
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMagic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Team
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ChargeLevel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.contains
import strikt.assertions.hasSize
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull

@OptIn(ExperimentalCoroutinesApi::class)
class ScenarioBattleStateBuilderTest {
    @Test
    fun `given no setters when build then returns documented defaults`() = runTest(UnconfinedTestDispatcher()) {
        // Given / When
        val state = ScenarioBattleStateBuilder().build()

        // Then
        expectThat(state.generalLevel).isEqualTo(0)
        expectThat(state.scenarioNumber).isNull()
        expectThat(state.name).isEqualTo("")
        expectThat(state.monsters).isEmpty()
        expectThat(state.golds).isEqualTo(0)
        expectThat(state.exp).isEqualTo(0)
        expectThat(state.trapDamage).isEqualTo(0)
        expectThat(state.gamersCount).isEqualTo(0)
        expectThat(state.monsterLevel).isEqualTo(0)
        expectThat(state.deck.getRemainingCards()).isEmpty()
        expectThat(state.activeMonsters).isEmpty()
        expectThat(state.round).isEqualTo(0)
        expectThat(state.availableEffects).isEmpty()
        Magic.entries.forEach { magic ->
            expectThat(state.magicState.charges.getValue(magic)).isEqualTo(ChargeLevel.Zero)
        }
    }

    @Test
    fun `given direct setters when build then values are propagated`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val monster = monster(slug = "brute")

        // When
        val state =
            ScenarioBattleStateBuilder()
                .name("Black Barrow")
                .monsters(mapOf("brute" to monster))
                .build()

        // Then
        expectThat(state.name).isEqualTo("Black Barrow")
        expectThat(state.monsters).isEqualTo(mapOf("brute" to monster))
    }

    @Test
    fun `given levelInfo when build then populates level golds exp trapDamage and monsterLevel`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val info = LevelInfo(level = 5, monsterLevel = 3, goldCount = 12, trapDamage = 4, experience = 7)

        // When
        val state = ScenarioBattleStateBuilder().levelInfo(info).build()

        // Then
        expectThat(state.generalLevel).isEqualTo(5)
        expectThat(state.golds).isEqualTo(12)
        expectThat(state.exp).isEqualTo(7)
        expectThat(state.trapDamage).isEqualTo(4)
        expectThat(state.monsterLevel).isEqualTo(3)
    }

    @Test
    fun `given team with MAIN pack only when build then availableEffects contains main effects pack`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = teamInfo(aliveCharacters = listOf(characterInfo(1), characterInfo(2)), packs = listOf(PackType.MAIN))

        // When
        val state = ScenarioBattleStateBuilder().team(team).build()

        // Then
        expectThat(state.gamersCount).isEqualTo(2)
        expectThat(state.availableEffects).isEqualTo(MonsterStatType.mainEffectsPack)
    }

    @Test
    fun `given team with FORGOTTEN_CIRCLES pack only when build then availableEffects equals fc effects pack`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = teamInfo(aliveCharacters = listOf(characterInfo(1)), packs = listOf(PackType.FORGOTTEN_CIRCLES))

        // When
        val state = ScenarioBattleStateBuilder().team(team).build()

        // Then
        expectThat(state.gamersCount).isEqualTo(1)
        expectThat(state.availableEffects).isEqualTo(MonsterStatType.fcEffectsPack)
    }

    @Test
    fun `given team with both packs when build then availableEffects is union of both packs`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val team = teamInfo(
            aliveCharacters = listOf(characterInfo(1), characterInfo(2), characterInfo(3)),
            packs = listOf(PackType.MAIN, PackType.FORGOTTEN_CIRCLES),
        )

        // When
        val state = ScenarioBattleStateBuilder().team(team).build()

        // Then
        expectThat(state.gamersCount).isEqualTo(3)
        expectThat(state.availableEffects).isEqualTo(MonsterStatType.mainEffectsPack + MonsterStatType.fcEffectsPack)
    }

    @Test
    fun `given gameState when build then round scenarioNumber and magicState are populated`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val gameState = gameState(
            round = 4,
            scenarioNumber = 12,
            magicCharges = listOf(ScenarioGameStateMagic(name = Magic.FIRE.name, value = 2)),
        )

        // When
        val state =
            ScenarioBattleStateBuilder()
                .monsters(emptyMap())
                .gameState(gameState)
                .build()

        // Then
        expectThat(state.round).isEqualTo(4)
        expectThat(state.scenarioNumber).isEqualTo(12)
        expectThat(state.magicState.charges.getValue(Magic.FIRE)).isEqualTo(ChargeLevel.Two)
        expectThat(state.magicState.charges.getValue(Magic.AIR)).isEqualTo(ChargeLevel.Zero)
    }

    @Test
    fun `given gameState with availableCards when build then deck is built from matching monster cards`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val cardA = MonsterCard(deckName = "boss", cardId = 1, actions = emptyList(), initiative = 10)
        val cardB = MonsterCard(deckName = "boss", cardId = 2, actions = emptyList(), initiative = 20)
        val monster = monster(slug = "brute", deckName = "boss", cards = listOf(cardA, cardB))
        val gameState = gameState(
            round = 1,
            scenarioNumber = 1,
            availableCards = listOf(
                AvailableCard(deck = "boss", cardId = 1),
                AvailableCard(deck = "boss", cardId = 999), // unknown card — must be skipped
            ),
        )

        // When
        val state =
            ScenarioBattleStateBuilder()
                .monsters(mapOf("brute" to monster))
                .gameState(gameState)
                .build()

        // Then
        expectThat(state.deck.getRemainingCards()).containsExactly(cardA)
    }

    @Test
    fun `given gameState with activeMonsters when build then they are forwarded to the inner builder and resolved`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val monster = monster(slug = "brute", deckName = "boss", life = 8)
        val stateUnit = ScenarioGameStateMonsterUnit(
            number = 1,
            currentLife = 8,
            level = 1,
            isElite = false,
            effects = emptySet(),
            isNew = false,
            maxLife = 8,
        )
        val activeItem = ScenarioGameStateMonsterItem(
            slug = "brute",
            currentCard = null,
            units = listOf(stateUnit),
        )
        val gameState = gameState(
            round = 0,
            scenarioNumber = 1,
            activeMonsters = listOf(activeItem),
            level = 1,
        )

        // When
        val state =
            ScenarioBattleStateBuilder()
                .levelInfo(LevelInfo(level = 1, monsterLevel = 1, goldCount = 0, trapDamage = 0, experience = 0))
                .team(teamInfo(aliveCharacters = listOf(characterInfo(1))))
                .monsters(mapOf("brute" to monster))
                .gameState(gameState)
                .build()

        // Then
        expectThat(state.activeMonsters).hasSize(1)
        expectThat(state.activeMonsters.keys).contains("brute")
        expectThat(state.activeMonsters["brute"]?.units[1]).isNotNull()
    }

    @Test
    fun `given DSL one-shot when buildScenarioBattleState invoked then lambda runs once and result equals apply build`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val monster = monster(slug = "brute")
        var invocationCount = 0

        // When
        val viaDsl =
            buildScenarioBattleState {
                invocationCount++
                name("Crypt")
                monsters(mapOf("brute" to monster))
            }
        val viaApply =
            ScenarioBattleStateBuilder()
                .apply {
                    name("Crypt")
                    monsters(mapOf("brute" to monster))
                }
                .build()

        // Then
        expectThat(invocationCount).isEqualTo(1)
        expectThat(viaDsl).isEqualTo(viaApply)
    }

    @Test
    fun `given DSL one-shot when lambda is suspend then it is awaited before build returns`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        suspend fun delayedName(): String = "Delayed"

        // When
        val state =
            buildScenarioBattleState {
                name(delayedName())
            }

        // Then
        expectThat(state.name).isEqualTo("Delayed")
    }

    private fun monster(
        slug: String,
        deckName: String = "deck",
        cards: List<MonsterCard> = emptyList(),
        life: Int = 10,
    ) = Monster(
        slug = slug,
        name = slug,
        life = life,
        stats = emptyList(),
        eliteLife = 20,
        eliteStats = emptyList(),
        deckName = deckName,
        cards = cards,
        isBoss = false,
        immunity = emptySet(),
        isFly = false,
        level = 1,
        lifeMultiple = false,
    )

    private fun teamInfo(
        aliveCharacters: List<CharacterInfo> = emptyList(),
        packs: List<PackType> = listOf(PackType.MAIN),
    ) = TeamInfo(
        id = 1,
        name = "Team",
        level = 1,
        teamAchievement = emptyList(),
        globalAchievement = emptyList(),
        reputation = 0,
        activeScenario = emptyList(),
        aliveCharacters = aliveCharacters,
        shopDiscount = 0,
        prosperity = Prosperity.fixture(),
        packs = packs,
        hasActiveScenario = false,
        churchValue = 0,
        difficultyLevel = DifficultyLevel.NORMAL,
    )

    private fun characterInfo(id: Int) =
        CharacterInfo(
            name = "Char$id",
            level = 1,
            characterType = CharacterClassType.Brute,
            isAlive = true,
            id = id,
            team = Team(teamId = 1, name = "T", packs = emptyList()),
            experience = 0,
            goldCount = 0,
            checkMarkCount = 0,
            notes = "",
            additionalContOfPerks = 0,
        )

    private fun gameState(
        round: Int = 0,
        scenarioNumber: Int? = null,
        availableCards: List<AvailableCard> = emptyList(),
        activeMonsters: List<ScenarioGameStateMonsterItem> = emptyList(),
        magicCharges: List<ScenarioGameStateMagic> = emptyList(),
        level: Int = 0,
        monsterSlugs: List<String> = emptyList(),
    ) = ScenarioGameState(
        level = level,
        scenarioNumber = scenarioNumber,
        monsterSlugs = monsterSlugs,
        round = round,
        availableCards = availableCards,
        activeMonsters = activeMonsters,
        magicCharges = magicCharges,
    )
}
