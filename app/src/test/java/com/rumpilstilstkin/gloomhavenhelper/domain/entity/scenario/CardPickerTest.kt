package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class CardPickerTest {
    @Test
    fun `given single-card deck when CardPicker Random picks then returns the only card`() {
        // Given
        val only = card("A", 1)

        // When
        val picked = CardPicker.Random.pick(listOf(only))

        // Then
        expectThat(picked).isEqualTo(only)
    }

    @Test
    fun `given deterministic picker when invoked then returns picker's choice`() {
        // Given
        val a = card("A", 1)
        val b = card("A", 2)
        val c = card("A", 3)
        val picker = CardPicker { cards -> cards[1] }

        // When
        val picked = picker.pick(listOf(a, b, c))

        // Then
        expectThat(picked).isEqualTo(b)
    }

    private fun card(
        deckName: String,
        cardId: Int,
    ) = MonsterCard(
        deckName = deckName,
        cardId = cardId,
        actions = emptyList(),
        initiative = cardId,
    )
}
