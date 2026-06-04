package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AvailableCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.containsExactlyInAnyOrder
import strikt.assertions.hasSize
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo
import strikt.assertions.isNull
import strikt.assertions.isSameInstanceAs

class MonsterDeckStateTest {
    @Test
    fun `given cards from multiple decks when create then groups by deckName`() {
        // Given
        val a1 = card("A", 1)
        val a2 = card("A", 2)
        val b1 = card("B", 1)

        // When
        val state = MonsterDeckState.create(listOf(a1, a2, b1))

        // Then
        expectThat(state.cardsForDeck("A")).containsExactly(a1, a2)
        expectThat(state.cardsForDeck("B")).containsExactly(b1)
    }

    @Test
    fun `given non-empty deck when drawCard with deterministic picker then card returned and removed`() {
        // Given
        val a1 = card("A", 1)
        val a2 = card("A", 2)
        val state = MonsterDeckState.create(listOf(a1, a2))

        // When
        val result = state.drawCard("A") { it.first() }

        // Then
        expectThat(result.card).isEqualTo(a1)
        expectThat(result.newState.cardsForDeck("A")).containsExactly(a2)
    }

    @Test
    fun `given unknown deck when drawCard then returns null and same state instance`() {
        // Given
        val state = MonsterDeckState.create(listOf(card("A", 1)))

        // When
        val result = state.drawCard("UNKNOWN")

        // Then
        expectThat(result.card).isNull()
        expectThat(result.newState).isSameInstanceAs(state)
    }

    @Test
    fun `given empty deck when drawCard then returns null and same state instance`() {
        // Given — a deck name that exists but has no cards isn't possible via create()
        // (groupBy only produces non-empty buckets). Use the no-such-deck path instead.
        val state = MonsterDeckState.create(emptyList())

        // When
        val result = state.drawCard("A")

        // Then
        expectThat(result.card).isNull()
        expectThat(result.newState).isSameInstanceAs(state)
    }

    @Test
    fun `given needsShuffle card when drawCard then card returned but deck preserved`() {
        // Given
        val shuffle = card("A", 1, needsShuffle = true)
        val regular = card("A", 2)
        val state = MonsterDeckState.create(listOf(shuffle, regular))

        // When
        val result = state.drawCard("A") { it.first() }

        // Then
        expectThat(result.card).isEqualTo(shuffle)
        expectThat(result.newState.cardsForDeck("A")).containsExactly(shuffle, regular)
        expectThat(result.newState).isSameInstanceAs(state)
    }

    @Test
    fun `given multiple decks when toAvailableCards then returns AvailableCard per remaining card`() {
        // Given
        val a1 = card("A", 1)
        val b1 = card("B", 1)
        val state = MonsterDeckState.create(listOf(a1, b1))

        // When
        val available = state.toAvailableCards()

        // Then
        expectThat(available).containsExactlyInAnyOrder(
            AvailableCard(deck = "A", cardId = 1),
            AvailableCard(deck = "B", cardId = 1),
        )
    }

    @Test
    fun `given multiple decks when getRemainingCards then returns flattened list`() {
        // Given
        val a1 = card("A", 1)
        val b1 = card("B", 1)
        val b2 = card("B", 2)
        val state = MonsterDeckState.create(listOf(a1, b1, b2))

        // When
        val remaining = state.getRemainingCards()

        // Then
        expectThat(remaining).hasSize(3)
        expectThat(remaining).containsExactlyInAnyOrder(a1, b1, b2)
    }

    @Test
    fun `given absent deck when cardsForDeck then returns empty list`() {
        // Given
        val state = MonsterDeckState.create(emptyList())

        // When / Then
        expectThat(state.cardsForDeck("missing")).isEmpty()
    }

    private fun card(
        deckName: String,
        cardId: Int,
        needsShuffle: Boolean = false,
    ) = MonsterCard(
        deckName = deckName,
        cardId = cardId,
        actions = emptyList(),
        needsShuffle = needsShuffle,
        initiative = cardId * 10,
    )
}
