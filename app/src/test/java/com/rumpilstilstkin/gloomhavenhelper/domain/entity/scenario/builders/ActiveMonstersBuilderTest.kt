package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.builders

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AvailableCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isFalse
import strikt.assertions.isNull
import strikt.assertions.isTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ActiveMonstersBuilderTest {
    @Test
    fun `given fresh builder when build then returns empty list`() = runTest(UnconfinedTestDispatcher()) {
        // Given / When
        val result = ActiveMonstersBuilder().build(gamersCount = 1) { _, _ -> null }

        // Then
        expectThat(result).hasSize(0)
    }

    @Test
    fun `given full population when build then returns mapped MonsterItems`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val card = MonsterCard(deckName = "boss", cardId = 1, actions = emptyList(), initiative = 10)
        val monster = monster(slug = "brute", deckName = "boss", cards = listOf(card), isFly = true, isBoss = true)
        val stateUnit = ScenarioGameStateMonsterUnit(
            number = 1,
            currentLife = 10,
            level = 1,
            isElite = false,
            effects = emptySet(),
            isNew = true,
            maxLife = 10,
        )
        val active = ScenarioGameStateMonsterItem(
            slug = "brute",
            currentCard = AvailableCard(deck = "boss", cardId = 1),
            units = listOf(stateUnit),
        )

        // When
        val result =
            ActiveMonstersBuilder()
                .scenarioMonsters(mapOf("brute" to monster))
                .activeMonsters(listOf(active))
                .cards(mapOf(("boss" to 1) to card))
                .levels(1 to 1)
                .build(gamersCount = 1) { _, _ -> null }

        // Then
        expectThat(result).hasSize(1)
        val item = result.first()
        expectThat(item.slug).isEqualTo("brute")
        expectThat(item.name).isEqualTo("brute")
        expectThat(item.isFly).isTrue()
        expectThat(item.isBoss).isTrue()
        expectThat(item.deck).isEqualTo("boss")
        expectThat(item.currentCard).isEqualTo(card)
        expectThat(item.units.toList().map { it.number }).containsExactly(1)
    }

    @Test
    fun `given persisted currentCard not in cards map when build then currentCard is null`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val monster = monster(slug = "brute", deckName = "boss")
        val active = ScenarioGameStateMonsterItem(
            slug = "brute",
            currentCard = AvailableCard(deck = "boss", cardId = 999),
            units = emptyList(),
        )

        // When
        val result =
            ActiveMonstersBuilder()
                .scenarioMonsters(mapOf("brute" to monster))
                .activeMonsters(listOf(active))
                .cards(emptyMap())
                .levels(1 to 1)
                .build(gamersCount = 1) { _, _ -> null }

        // Then
        expectThat(result.first().currentCard).isNull()
    }

    @Test
    fun `given null persisted currentCard when build then currentCard is null`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val monster = monster(slug = "brute")
        val active = ScenarioGameStateMonsterItem(slug = "brute", currentCard = null, units = emptyList())

        // When
        val result =
            ActiveMonstersBuilder()
                .scenarioMonsters(mapOf("brute" to monster))
                .activeMonsters(listOf(active))
                .cards(emptyMap())
                .levels(1 to 1)
                .build(gamersCount = 1) { _, _ -> null }

        // Then
        expectThat(result.first().currentCard).isNull()
    }

    @Test
    fun `given active monster slug not in scenarioMonsters when build then throws NoSuchElementException`() {
        // Given
        val active = ScenarioGameStateMonsterItem(slug = "missing", currentCard = null, units = emptyList())
        val builder =
            ActiveMonstersBuilder()
                .scenarioMonsters(emptyMap())
                .activeMonsters(listOf(active))
                .cards(emptyMap())
                .levels(1 to 1)

        // When / Then
        assertThrows(NoSuchElementException::class.java) {
            runTest(UnconfinedTestDispatcher()) {
                builder.build(gamersCount = 1) { _, _ -> null }
            }
        }
    }

    @Test
    fun `given non-boss non-fly monster when build then flags are propagated`() = runTest(UnconfinedTestDispatcher()) {
        // Given
        val monster = monster(slug = "scout", isFly = false, isBoss = false)
        val active = ScenarioGameStateMonsterItem(slug = "scout", currentCard = null, units = emptyList())

        // When
        val result =
            ActiveMonstersBuilder()
                .scenarioMonsters(mapOf("scout" to monster))
                .activeMonsters(listOf(active))
                .cards(emptyMap())
                .levels(1 to 1)
                .build(gamersCount = 1) { _, _ -> null }

        // Then
        expectThat(result.first().isFly).isFalse()
        expectThat(result.first().isBoss).isFalse()
    }

    private fun monster(
        slug: String,
        deckName: String = "deck",
        cards: List<MonsterCard> = emptyList(),
        isFly: Boolean = false,
        isBoss: Boolean = false,
    ) = Monster(
        slug = slug,
        name = slug,
        life = 10,
        stats = emptyList(),
        eliteLife = 20,
        eliteStats = emptyList(),
        deckName = deckName,
        cards = cards,
        isBoss = isBoss,
        immunity = emptySet(),
        isFly = isFly,
        level = 1,
        lifeMultiple = false,
    )
}
